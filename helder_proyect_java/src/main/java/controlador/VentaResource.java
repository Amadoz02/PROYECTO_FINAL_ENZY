package controlador;

import modelo.Venta;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import modelo.VentaDAO;

@Path("/ventas")
public class VentaResource {

    private VentaDAO dao = new VentaDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVentas() throws Exception {
        List<Venta> ventas = dao.obtenerVentas();
        return Response.ok(ventas).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVentaPorId(@PathParam("id") int id) throws Exception {
        Venta venta = dao.obtenerVentaPorId(id);
        if (venta != null) {
            return Response.ok(venta).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Venta no encontrada").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response crearVenta(Venta venta) throws Exception {
        boolean creada = dao.crearVenta(venta);
        if (creada) {
            return Response.status(Response.Status.CREATED).entity("Venta creada correctamente").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al crear venta").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response actualizarVenta(@PathParam("id") int id, Venta venta) throws Exception {
        venta.setId_venta(id);
        boolean actualizada = dao.actualizarVenta(venta);
        if (actualizada) {
            return Response.ok("Venta actualizada correctamente").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No se pudo actualizar la venta").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response eliminarVenta(@PathParam("id") int id) throws Exception {
        boolean eliminada = dao.eliminarVenta(id);
        if (eliminada) {
            return Response.ok("Venta eliminada correctamente").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No se pudo eliminar la venta").build();
        }
    }
}
