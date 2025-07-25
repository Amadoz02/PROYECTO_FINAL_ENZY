/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author Administrador
 */
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Categoria;
import modelo.CategoriaDAO;

import java.util.List;

/**
 * API REST para gestionar las operaciones CRUD de la tabla 'categorias'.
 */
@Path("/categorias") // Ruta accesible desde el frontend: /api/categorias
@Produces(MediaType.APPLICATION_JSON) // Todas las respuestas serán en JSON
@Consumes(MediaType.APPLICATION_JSON) // Se espera recibir datos en formato JSON
public class CategoriaResource {

    CategoriaDAO dao = new CategoriaDAO(); // Instancia para acceder a la base de datos

    // Ruta: GET /api/categorias - Listar todas las categorías
    @GET
    public List<Categoria> listarCategorias() {
        return dao.listar();
    }

    // Ruta: POST /api/categorias - Insertar una nueva categoría
    @POST
    public Response agregarCategoria(Categoria categoria) throws Exception {
        boolean creada = dao.insertar(categoria);

        if (creada) {
            return Response.status(Response.Status.CREATED).entity("{\"mensaje\": \"Categoría creada correctamente.\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"No se pudo crear la categoría.\"}").build();
        }
    }

    // Ruta: PUT /api/categorias - Actualizar una categoría
    @PUT
    public Response actualizarCategoria(Categoria categoria) {
        boolean actualizada = dao.actualizar(categoria);

        if (actualizada) {
            return Response.ok("{\"mensaje\": \"Categoría actualizada correctamente.\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"No se pudo actualizar la categoría.\"}").build();
        }
    }

    // Ruta: DELETE /api/categorias/{id} - Eliminar categoría por ID
    @DELETE
    @Path("/{id}")
    public Response eliminarCategoria(@PathParam("id") int id) {
        boolean eliminada = dao.eliminar(id);

        if (eliminada) {
            return Response.ok("{\"mensaje\": \"Categoría eliminada correctamente.\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"Categoría no encontrada.\"}").build();
        }
    }
}