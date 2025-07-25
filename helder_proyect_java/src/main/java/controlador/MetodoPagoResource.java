package controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.MetodoPago;
import modelo.MetodoPagoDAO;
import java.sql.*;
import java.util.*;

@Path("/metodos")
public class MetodoPagoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MetodoPago> obtenerTodos() throws Exception {
        Connection con = Conexion.getConexion();
        MetodoPagoDAO dao = new MetodoPagoDAO(con);
        return dao.obtenerTodos();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void agregarMetodo(MetodoPago m) throws Exception {
        Connection con = Conexion.getConexion();
        MetodoPagoDAO dao = new MetodoPagoDAO(con);
        dao.insertar(m);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizarMetodo(MetodoPago m) throws Exception {
        Connection con = Conexion.getConexion();
        MetodoPagoDAO dao = new MetodoPagoDAO(con);
        dao.actualizar(m);
    }

    @DELETE
    @Path("/{id}")
    public void eliminarMetodo(@PathParam("id") int id_metodo) throws Exception {
        Connection con = Conexion.getConexion();
        MetodoPagoDAO dao = new MetodoPagoDAO(con);
        dao.eliminar(id_metodo);
    }
}
