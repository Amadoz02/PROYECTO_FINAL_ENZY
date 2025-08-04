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
        // GET /favoritos/usuario/{idUsuario}
    @GET
    @Path("/usuario/{idUsuario}")
    public Response getFavoritosPorUsuario(@PathParam("idUsuario") int idUsuario) {
        System.out.println("Llamada a /favoritos/usuario/" + idUsuario);
        List<Favorito> favoritos = dao.obtenerPorIdUsuario(idUsuario);
        return Response.ok(favoritos).build();  // Siempre responde 200 con lista (vacía o no)
    }





    // POST /favoritos
     @POST
    public Response crearFavorito(Favorito favorito) {
        try {
            if (!dao.usuarioExiste(favorito.getIdUsuario()) || !dao.productoExiste(favorito.getIdProducto())) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Usuario o producto no válido").build();
            }

            if (dao.insertar(favorito)) {
                return Response.status(Response.Status.CREATED).build();
            } else {
                return Response.status(Response.Status.CONFLICT)
                    .entity("El favorito ya existe").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al agregar a favoritos").build();
        }
    }


    // DELETE favoritos/{id_usuario}/{id_producto}
    @DELETE
    @Path("/{id_usuario}/{id_producto}")
    public Response eliminar(@PathParam("id_usuario") int id_usuario, @PathParam("id_producto") int id_producto) throws Exception {
        if (dao.eliminarPorUsuarioYProducto(id_usuario, id_producto)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    // GET favoritos/{id_usuario}/{id_producto}
    @GET
    @Path("/{id_usuario}/{id_producto}")
    public List<Favorito> listarPorProducto(@PathParam("id_usuario") int id_usuario, @PathParam("id_producto") int id_producto) {
        return dao.listarPorId(id_usuario, id_producto);
    }
}
