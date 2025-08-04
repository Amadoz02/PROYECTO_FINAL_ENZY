package controlador;

import jakarta.ws.rs.*; // Importa las anotaciones de JAX-RS para crear servicios REST
import jakarta.ws.rs.core.MediaType; // Importa la clase MediaType para definir tipos de contenido
import jakarta.ws.rs.core.Response;
import java.sql.Connection; // Importa la clase Connection para manejar conexiones a la base de datos
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List; // Importa la interfaz List
import modelo.Carrito;
import modelo.DetalleCarrito; // Importa la clase DetalleCarrito
import modelo.DetalleCarritoDAO; // Importa la clase DetalleCarritoDAO

/**
 * Recurso REST para manejar operaciones sobre la tabla detalles_carrito.
 * Ruta base: http://localhost:8080/helder/api/detalles_carrito
 */
@Path("/detalles_carrito")
public class DetalleCarritoResource {

    public  DetalleCarritoDAO detalleDAO;
    public  CarritoService carritoServices;

    public DetalleCarritoResource() throws Exception {
        try {
            Connection con = Conexion.getConexion();
            this.detalleDAO = new DetalleCarritoDAO(con);
            this.carritoServices = new CarritoService(con);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo inicializar DetalleCarritoResource", e);
        }
    }

    @GET
    @Path("/usuario/{usuarioId}") // Ruta para obtener detalles del carrito por ID de usuario
    @Produces(MediaType.APPLICATION_JSON)
    public List<DetalleCarrito> obtenerDetallesPorUsuario(@PathParam("usuarioId") int usuarioId) throws Exception {
        Connection con = null; // Inicializa la conexión
        try {
            con = Conexion.getConexion(); // Obtiene la conexión a la base de datos
            DetalleCarritoDAO dao = new DetalleCarritoDAO(con); // Crea una instancia de DetalleCarritoDAO
            Carrito carrito = carritoServices.obtenerCarritoActivo(usuarioId); // Obtiene el carrito activo del usuario
            return dao.obtenerPorIdCarrito(carrito.getId_carrito()); // Devuelve los detalles del carrito
        } catch (Exception e) {
            System.err.println("Error al obtener los detalles del carrito por usuario: " + e.getMessage());
            throw e; // Vuelve a lanzar la excepción para que el llamador la maneje
        } finally {
            if (con != null) {
                con.close(); // Cierra la conexión si está abierta
            }
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON) // Especifica que el método consume JSON
    @Produces(MediaType.APPLICATION_JSON) // Especifica que el método produce JSON

    public Response agregarProductoAlCarrito(
            @HeaderParam("usuarioId") int usuarioId,
            DetalleCarrito detalle) throws Exception {
        try {
            Carrito carrito = carritoServices.obtenerCarritoActivo(usuarioId);
            detalle.setId_carrito(carrito.getId_carrito());

            DetallesCarritoService detalleService = new DetallesCarritoService();
            detalleService.agregarOActualizarDetalle(detalle);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Producto agregado al carrito\"}")
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error al agregar producto\"}")
                    .build();
        }
    }
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
     * Actualiza un detalle existente.
     * @param detalle objeto DetalleCarrito con los nuevos datos.
     * @throws Exception si ocurre un error al actualizar el detalle.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON) // Especifica que el método consume JSON
    public void actualizar(@PathParam("id") int id,DetalleCarrito detalle) throws Exception {
        detalle.setId_detalle(id);
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
