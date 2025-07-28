package controlador;

import modelo.FavoritoDAO;
import modelo.Favorito;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/favoritos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FavoritoResource {

    FavoritoDAO dao = new FavoritoDAO();

    // GET /favoritos
    @GET
    public List<Favorito> getFavoritos() {
        return dao.obtenerTodos();
    }

    // GET /favoritos/{id}
    @GET
    @Path("/{idProducto}")
    public Response getFavoritoPorId(@PathParam("idProducto") int id) {
        Favorito f = dao.obtenerPorId(id);
        if (f != null) {
            return Response.ok(f).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // POST /favoritos
        @POST
    public Response crearFavorito(Favorito favorito) {
        try {
            System.out.println("Favorito recibido:");
            System.out.println("Usuario ID: " + favorito.getIdUsuario());
            System.out.println("Producto ID: " + favorito.getIdProducto());

           if (dao.insertar(favorito)) {
            return Response.status(Response.Status.CREATED).build();
            } else {
                return Response.serverError().build();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para ver el error exacto en la consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al agregar a favoritos").build();
        }
    }

    // DELETE 
    @DELETE
    @Path("/{id_usuario}/{id_producto}")
    public Response eliminar(@PathParam("id_usuario") int id_usuario, @PathParam("id_producto") int id_producto) throws Exception {
        if (dao.eliminarPorUsuarioYProducto(id_usuario, id_producto)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    // GET /favoritos/producto/{idProducto}
    @GET
    @Path("/producto/{idProducto}")
    public List<Favorito> listarPorProducto(@PathParam("idProducto") int idProducto) {
        return dao.listarPorIdProducto(idProducto);
    }
}
