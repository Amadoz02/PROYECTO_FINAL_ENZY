package controlador;

import jakarta.ws.rs.*;                          // Importa las anotaciones necesarias para REST
import jakarta.ws.rs.core.*;                    // Importa tipos de respuesta y contexto
import java.util.List;                          // Para manejar listas
import modelo.Direccion;
import modelo.DireccionDAO;



/**
 * Esta clase expone servicios web (API REST) para trabajar con direcciones.
 * Aquí puedes obtener, crear, actualizar o eliminar direcciones.
 */
@Path("/direcciones")                          // Ruta base de este recurso: /api/direcciones
@Produces(MediaType.APPLICATION_JSON)          // Todas las respuestas serán en formato JSON
@Consumes(MediaType.APPLICATION_JSON)          // Se acepta contenido en formato JSON
public class DireccionResource {

    // Se crea una instancia del DAO para acceder a los métodos de la base de datos
    DireccionDAO dao = new DireccionDAO();

    /**
     * Método para obtener la lista completa de direcciones.
     * Se ejecuta con: GET /api/direcciones
     */
    @GET
    public Response listar() {
        try {
            List<Direccion> lista = dao.listarDirecciones();          // Obtener todas las direcciones
            return Response.ok(lista).build();                        // Retorna código 200 OK con la lista
        } catch (Exception e) {
            e.printStackTrace();                                      // Muestra error en consola si ocurre
            return Response.status(500).entity("{\"error\": \"Error al listar direcciones\"}").build();
        }
    }

    /**
     * Método para obtener una dirección específica por su ID.
     * Se ejecuta con: GET /api/direcciones/{id}
     */
    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") int id) {
        try {
            Direccion d = dao.buscarPorId(id);                        // Busca la dirección por ID
            if (d != null) {
                return Response.ok(d).build();                        // Si la encuentra, la devuelve
            } else {
                return Response.status(404).entity("{\"error\": \"Dirección no encontrada\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();                                      // Imprime error si algo falla
            return Response.status(500).entity("{\"error\": \"Error al obtener la dirección\"}").build();
        }
    }

    /**
     * Método para registrar una nueva dirección.
     * Se ejecuta con: POST /api/direcciones
     */
    @POST
    public Response crear(Direccion direccion) {
        try {
            boolean creado = dao.insertar(direccion);                 // Intenta insertar la dirección
            if (creado) {
                return Response.status(201).entity("{\"mensaje\": \"Dirección registrada exitosamente\"}").build();
            } else {
                return Response.status(400).entity("{\"error\": \"No se pudo registrar la dirección\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();                                      // Muestra error en consola
            return Response.status(500).entity("{\"error\": \"Error al registrar la dirección\"}").build();
        }
    }

    /**
     * Método para actualizar una dirección existente.
     * Se ejecuta con: PUT /api/direcciones/{id}
     */
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") int id, Direccion direccion) {
        try {
            Direccion existente = dao.buscarPorId(id);                // Verifica si la dirección existe
            if (existente == null) {
                return Response.status(404).entity("{\"error\": \"Dirección no encontrada\"}").build();
            }

            direccion.setId_direccion(id);                            // Asegura que el ID sea el correcto
            boolean actualizado = dao.actualizar(direccion);          // Intenta actualizar la dirección

            if (actualizado) {
                return Response.ok("{\"mensaje\": \"Dirección actualizada correctamente\"}").build();
            } else {
                return Response.status(400).entity("{\"error\": \"No se pudo actualizar\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();                                      // Muestra error en consola
            return Response.status(500).entity("{\"error\": \"Error al actualizar\"}").build();
        }
    }

    /**
     * Método para eliminar una dirección por ID.
     * Se ejecuta con: DELETE /api/direcciones/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        try {
            Direccion existente = dao.buscarPorId(id);                // Verifica si existe la dirección
            if (existente == null) {
                return Response.status(404).entity("{\"error\": \"Dirección no encontrada\"}").build();
            }

            boolean eliminado = dao.eliminar(id);                     // Intenta eliminarla

            if (eliminado) {
                return Response.ok("{\"mensaje\": \"Dirección eliminada\"}").build();
            } else {
                return Response.status(400).entity("{\"error\": \"No se pudo eliminar\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();                                      // Imprime error si hay fallo
            return Response.status(500).entity("{\"error\": \"Error al eliminar\"}").build();
        }
    }
}
