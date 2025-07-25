package controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Rol;
import modelo.RolDAO;


/**
 * Clase que expone servicios REST para gestionar roles de forma sencilla.
 */
@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RolResource {

    // Creamos una instancia del DAO para usar sus métodos
    RolDAO dao = new RolDAO();

    /**
     * Método para obtener todos los roles.
     * URL: GET /api/roles
     */
    @GET
    public List<Rol> obtenerTodosLosRoles() throws Exception {
        
        return dao.obtenerTodos();
    }

    /**
     * Método para obtener un rol por su ID.
     * URL: GET /api/roles/{id}
     */
    @GET
    @Path("/{id}")
    public Rol obtenerRolPorId(@PathParam("id") int id) throws Exception {
        return dao.obtenerPorId(id);
    }

    /**
     * Método para crear un nuevo rol.
     * URL: POST /api/roles
     */
    @POST
    public Response crearRol(Rol nuevoRol) throws Exception {
        boolean creado = dao.insertar(nuevoRol);
        if (creado) {
            return Response.status(Response.Status.CREATED).entity(nuevoRol).build(); // 201 Created
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); // 500 Error
        }
    }

    /**
     * Método para actualizar un rol existente.
     * URL: PUT /api/roles/{id}
     */
    @PUT
    @Path("/{id}")
    public Response actualizarRol(@PathParam("id") int id, Rol rol) {
        rol.setId_rol(id); // Aseguramos que el ID sea el correcto
        boolean actualizado = dao.actualizar(rol);
        if (actualizado) {
            return Response.ok().build(); // 200 OK
        } else {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404 No encontrado
        }
    }

    /**
     * Método para eliminar un rol por su ID.
     * URL: DELETE /api/roles/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response eliminarRol(@PathParam("id") int id) {
        boolean eliminado = dao.eliminar(id);
        if (eliminado) {
            return Response.noContent().build(); // 204 No Content
        } else {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404 No encontrado
        }
    }
}
