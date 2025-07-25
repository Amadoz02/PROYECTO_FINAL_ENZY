package controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import modelo.Genero;
import modelo.GeneroDAO;
import java.util.List;

@Path("generos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GeneroResource {

    // GET: /api/generos
    @GET
    public List<Genero> listar() throws Exception {
        return new GeneroDAO().listar();
    }

    // GET: /api/generos/{id}
    @GET
    @Path("{id}")
    public Genero buscarPorId(@PathParam("id") int id) throws Exception {
        return new GeneroDAO().buscarPorId(id);
    }

    // POST: /api/generos
    @POST
    public Response insertar(Genero genero) throws Exception {
        if (genero.getTipo_genero() == null || genero.getTipo_genero().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("El campo tipo_genero es obligatorio.")
                           .build();
        }

        new GeneroDAO().insertar(genero);
        return Response.status(Response.Status.CREATED).build();
    }

    // PUT: /api/generos
    @PUT
    public Response actualizar(Genero genero) throws Exception {
        if (genero.getId_genero() == 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("El campo id_genero es obligatorio para actualizar.")
                           .build();
        }

        new GeneroDAO().actualizar(genero);
        return Response.ok().build();
    }

    // DELETE: /api/generos/{id}
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") int id) throws Exception {
        new GeneroDAO().eliminar(id);
        return Response.ok().build();
    }
}
