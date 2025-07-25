package controlador;

import jakarta.ws.rs.*; // Importa las anotaciones de JAX-RS para crear servicios REST
import jakarta.ws.rs.core.MediaType; // Importa la clase MediaType para definir tipos de contenido
import java.sql.Connection; // Importa la clase Connection para manejar conexiones a la base de datos
import java.util.List; // Importa la interfaz List
import modelo.DetalleCarrito; // Importa la clase DetalleCarrito
import modelo.DetalleCarritoDAO; // Importa la clase DetalleCarritoDAO

/**
 * Recurso REST para manejar operaciones sobre la tabla detalles_carrito.
 * Ruta base: http://localhost:8080/helder/api/detalles_carrito
 */
@Path("/detalles_carrito")
public class DetalleCarritoResource {

    /**
     * Obtiene todos los detalles de carrito.
     * @return lista de objetos DetalleCarrito en formato JSON.
     * @throws Exception si ocurre un error al obtener los detalles.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON) // Especifica que el método produce JSON
    public List<DetalleCarrito> obtenerTodos() throws Exception {
        Connection con = null; // Inicializa la conexión
        try {
            con = Conexion.getConexion(); // Obtiene la conexión a la base de datos
            DetalleCarritoDAO dao = new DetalleCarritoDAO(con); // Crea una instancia de DetalleCarritoDAO
            return dao.obtenerTodos(); // Devuelve la lista de detalles
        } catch (Exception e) {
            // Mensaje de error personalizado para problemas al obtener detalles
            System.err.println("Error al obtener los detalles del carrito: " + e.getMessage());
            throw e; // Vuelve a lanzar la excepción para que el llamador la maneje
        } finally {
            if (con != null) {
                con.close(); // Cierra la conexión si está abierta
            }
        }
    }

    /**
     * Inserta un nuevo detalle de carrito.
     * @param detalle objeto DetalleCarrito recibido en el cuerpo del JSON.
     * @throws Exception si ocurre un error al insertar el detalle.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON) // Especifica que el método consume JSON
    public void insertar(DetalleCarrito detalle) throws Exception {
        Connection con = null; // Inicializa la conexión
        try {
            con = Conexion.getConexion(); // Obtiene la conexión a la base de datos
            DetalleCarritoDAO dao = new DetalleCarritoDAO(con); // Crea una instancia de DetalleCarritoDAO
            dao.insertar(detalle); // Inserta el detalle
        } catch (Exception e) {
            // Mensaje de error personalizado para problemas al insertar
            System.err.println("Error al insertar el detalle del carrito: " + e.getMessage());
            throw e; // Vuelve a lanzar la excepción para que el llamador la maneje
        } finally {
            if (con != null) {
                con.close(); // Cierra la conexión si está abierta
            }
        }
    }

    /**
     * Actualiza un detalle existente.
     * @param detalle objeto DetalleCarrito con los nuevos datos.
     * @throws Exception si ocurre un error al actualizar el detalle.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON) // Especifica que el método consume JSON
    public void actualizar(DetalleCarrito detalle) throws Exception {
        Connection con = null; // Inicializa la conexión
        try {
            con = Conexion.getConexion(); // Obtiene la conexión a la base de datos
            DetalleCarritoDAO dao = new DetalleCarritoDAO(con); // Crea una instancia de DetalleCarritoDAO
            dao.actualizar(detalle); // Actualiza el detalle
        } catch (Exception e) {
            // Mensaje de error personalizado para problemas al actualizar
            System.err.println("Error al actualizar el detalle del carrito: " + e.getMessage());
            throw e; // Vuelve a lanzar la excepción para que el llamador la maneje
        } finally {
            if (con != null) {
                con.close(); // Cierra la conexión si está abierta
            }
        }
    }

    /**
     * Elimina un detalle según su ID.
     * @param id identificador del detalle a eliminar.
     * @throws Exception si ocurre un error al eliminar el detalle.
     */
    @DELETE
    @Path("/{id}") // Especifica que el ID se pasa como parte de la ruta
    public void eliminar(@PathParam("id") int id) throws Exception {
        Connection con = null; // Inicializa la conexión
        try {
            con = Conexion.getConexion(); // Obtiene la conexión a la base de datos
            DetalleCarritoDAO dao = new DetalleCarritoDAO(con); // Crea una instancia de DetalleCarritoDAO
            dao.eliminar(id); // Elimina el detalle por ID
        } catch (Exception e) {
            // Mensaje de error personalizado para problemas al eliminar
            System.err.println("Error al eliminar el detalle del carrito: " + e.getMessage());
            throw e; // Vuelve a lanzar la excepción para que el llamador la maneje
        } finally {
            if (con != null) {
                con.close(); // Cierra la conexión si está abierta
            }
        }
    }
}
