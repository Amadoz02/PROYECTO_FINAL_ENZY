package controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.Carrito;
import modelo.CarritoDAO;

import java.sql.Connection;
import java.util.List;

/**
 * Recurso REST para la entidad Carrito
 * Ruta base: /carritos
 */
@Path("/carritos")
public class CarritoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Carrito> obtenerTodos() throws Exception {
        Connection con = Conexion.getConexion();
        CarritoDAO dao = new CarritoDAO(con);
        return dao.obtenerTodos();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void agregarCarrito(Carrito carrito) throws Exception {
        Connection con = Conexion.getConexion();
        CarritoDAO dao = new CarritoDAO(con);
        dao.insertar(carrito);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizarCarrito(Carrito carrito) throws Exception {
        Connection con = Conexion.getConexion();
        CarritoDAO dao = new CarritoDAO(con);
        dao.actualizar(carrito);
    }

    @DELETE
    @Path("/{id}")
    public void eliminarCarrito(@PathParam("id") int id_carrito) throws Exception {
        Connection con = Conexion.getConexion();
        CarritoDAO dao = new CarritoDAO(con);
        dao.eliminar(id_carrito);
    }
}
