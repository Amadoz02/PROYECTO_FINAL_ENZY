/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import modelo.Imagen;
import modelo.ImagenDAO;
import org.glassfish.jersey.media.multipart.*;

import java.io.*;
import java.util.*;
import java.util.UUID;
import jakarta.servlet.ServletContext;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Path("/imagenes")
public class ImagenResource {

    @Context
    ServletContext contexto;

    ImagenDAO dao = new ImagenDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Imagen> listar() throws Exception {
        return dao.listar();
    }

    @POST
    @Path("/subir")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirImagen(
            @FormDataParam("imagen") InputStream imagenInputStream,
            @FormDataParam("imagen") FormDataContentDisposition fileDetail,
            @FormDataParam("id_producto") int idProducto,
            @FormDataParam("descripcion") String descripcion) {

        if (imagenInputStream == null || fileDetail == null || fileDetail.getFileName() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Falta el archivo de imagen\"}")
                    .build();
        }

        try {
            // Obtener ruta física al directorio webapp/imagenes
            // Guardar en carpeta real del proyecto
           // Usamos una clase que sí esté dentro del proyecto para calcular la ruta base
                       // Ruta real del proyecto fuera de /target
            String rutaBase = contexto.getRealPath("/");
            File carpetaProyecto = new File(rutaBase).getParentFile().getParentFile();
            String rutaWebapp = new File(carpetaProyecto, "src/main/webapp/IMAGENES").getAbsolutePath();
            



            // Crear carpeta si no existe
            File carpeta = new File(rutaWebapp);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
                System.out.println("[INFO] Carpeta creada en: " + rutaWebapp);
            }

            // Generar nombre único
            String nombreOriginal = fileDetail.getFileName();
            String nombreFinal = UUID.randomUUID().toString() + "_" + nombreOriginal;
            String rutaFinal = rutaWebapp + File.separator + nombreFinal;

            // Guardar imagen físicamente
            Files.copy(imagenInputStream, Paths.get(rutaFinal), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("[INFO] Imagen guardada en: " + rutaFinal);

            // URL pública
            String urlPublica = "http://localhost:8080/helder/imagenes/" + nombreFinal;

            // Guardar en BD
            Imagen img = new Imagen();
            img.setId_producto(idProducto);
            img.setDescripcion(descripcion);
            img.setUrl_imagen(urlPublica);
            dao.insertar(img);

            return Response.ok(img).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error al subir imagen\"}")
                    .build();
        }
    }




    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) throws Exception {
        boolean eliminado = dao.eliminar(id);
        if (eliminado) {
            return Response.ok().build();
        } else {
            return Response.status(404).entity("{\"error\":\"Imagen no encontrada.\"}").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(Imagen img) throws Exception {
        boolean actualizado = dao.actualizar(img);
        if (actualizado) {
            return Response.ok(img).build();
        } else {
            return Response.status(500).entity("{\"error\":\"No se pudo actualizar.\"}").build();
        }
    }
}
