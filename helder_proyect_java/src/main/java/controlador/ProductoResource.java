package controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.Producto;
import modelo.ProductoDAO;

import java.util.List;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoResource {

    @GET
    public List<Producto> getProductos() throws Exception {
        ProductoDAO dao = new ProductoDAO();
        return dao.obtenerTodos();
    }

    @GET
    @Path("/{id}")
    public Producto getProducto(@PathParam("id") int id) throws Exception {
        ProductoDAO dao = new ProductoDAO();
        return dao.obtenerPorId(id);
    }

    @POST
    public void crearProducto(Producto p) throws Exception {
        ProductoDAO dao = new ProductoDAO();
        dao.insertar(p);
    }

    @PUT
    @Path("/{id}")
    public void actualizarProducto(@PathParam("id") int id, Producto p) throws Exception {
        p.setId_producto(id);
        ProductoDAO dao = new ProductoDAO();
        dao.actualizar(p);
    }

    @DELETE
    @Path("/{id}")
    public void eliminarProducto(@PathParam("id") int id) throws Exception {
        ProductoDAO dao = new ProductoDAO();
        dao.eliminar(id);
    }
}
