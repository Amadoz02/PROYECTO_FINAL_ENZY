package controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import modelo.Usuario;
import modelo.UsuarioDAO;
import static controlador.Utilidades.encriptarSHA256;

/**
 * Recurso REST para gestionar los usuarios del sistema.
 * Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * en formato JSON usando JAX-RS.
 */
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    // Se instancia el DAO que contiene la lógica de conexión a la base de datos.
    private UsuarioDAO dao = new UsuarioDAO();

    /**
     * Método GET general.
     * Permite obtener una lista con todos los usuarios registrados.
     * 
     * @return lista de objetos Usuario
     */
    @GET
    public Response getUsuarios() {
        try {
            List<Usuario> lista = dao.listarUsuarios();

            return Response.ok(lista).build(); // Código 200 OK con la lista como respuesta
        } catch (Exception e) {
            e.printStackTrace();//nos muestra las exepciones del servidor
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\": \"Error al listar usuarios\"}")
                           .build();
        }
    }

    /**
     * Método GET por ID.
     * Permite consultar un usuario específico según su ID.
     * 
     * @param id del usuario a buscar
     * @return objeto Usuario si existe, o mensaje de error 404 si no se encuentra
     */
    @GET
    @Path("/{id}")
    public Response getUsuario(@PathParam("id") int id) {
        try {
            Usuario u = dao.buscarPorId(id);
            if (u != null) {
                return Response.ok(u).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\": \"Usuario no encontrado\"}")
                               .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(501)
                           .entity("{\"error\": \"Error al buscar usuario\"}")
                           .build();
        }
    }

/**
 * Método POST.
 * Permite agregar un nuevo usuario al sistema.
 * Se encripta la contraseña antes de guardar.
 * 
 * @param usuario datos recibidos en formato JSON
 * @return respuesta HTTP 201 si fue creado, o error si falla
 */
@POST
public Response agregarUsuario(Usuario usuario) {
    try {
        // Se encripta la contraseña antes de guardar
        //usuario.setContrasena(encriptarSHA256(usuario.getContrasena()));

        // Suponiendo que el método insertar devuelve el ID del nuevo usuario
        int idUsuarioCreado = dao.insertar(usuario);
        if (idUsuarioCreado > 0) {
            // Devolver el ID del usuario creado junto con el mensaje
            return Response.status(Response.Status.CREATED)
                           .entity("{\"id_usuario\": " + idUsuarioCreado + ", \"mensaje\": \"Usuario creado correctamente\"}")
                           .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\": \"No se pudo registrar el usuario\"}")
                           .build();
        }
    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(500)
                       .entity("{\"error\": \"" + e.getMessage() + "\"}")
                       .build();
    }
}


    /**
     * Método PUT.
     * Permite actualizar los datos de un usuario existente.
     * También se actualiza la contraseña encriptada si se envía una nueva.
     * 
     * @param id del usuario a modificar
     * @param usuario objeto con los nuevos datos
     * @return código 200 si se actualiza correctamente, 404 si no se encuentra
     */
    @PUT
    @Path("/{id}")
    public Response actualizarUsuario(@PathParam("id") int id, Usuario usuario) {
        try {
            // Validar si el usuario existe antes de intentar actualizar
            Usuario existente = dao.buscarPorId(id);
            if (existente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\": \"Usuario no encontrado\"}")
                               .build();
            }

            // Se prepara el objeto con el nuevo ID y la contraseña encriptada
            usuario.setId_usuario(id);
            usuario.setContrasena(encriptarSHA256(usuario.getContrasena()));

            boolean actualizado = dao.actualizar(usuario);
            if (actualizado) {
                return Response.ok("{\"mensaje\": \"Usuario actualizado correctamente\"}").build();
            } else {
                return Response.status(500)
                               .entity("{\"error\": \"Error al actualizar el usuario\"}")
                               .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    /**
     * Método DELETE.
     * Permite eliminar un usuario por su ID.
     * 
     * @param id del usuario a eliminar
     * @return código 200 si fue eliminado, 500 si no se encuentra
     */
    @DELETE
    @Path("/{id}")
    public Response eliminarUsuario(@PathParam("id") int id) {
        try {
            Usuario existente = dao.buscarPorId(id);
            if (existente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\": \"Usuario no encontrado\"}")
                               .build();
            }

            boolean eliminado = dao.eliminar(id);
            if (eliminado) {
                return Response.ok("{\"mensaje\": \"Usuario eliminado correctamente\"}").build();
            } else {
                return Response.status(500)
                               .entity("{\"error\": \"No se pudo eliminar el usuario\"}")
                               .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        }
    }
    
        /**
     * Método PATCH - Actualización parcial del usuario.
     * Solo modifica los campos que vienen en el objeto JSON.
     *
     * @param id ID del usuario a modificar
     * @param datos Datos parciales a modificar (pueden ser 1 o varios campos)
     * @return Código 200 si se actualiza, 404 si no se encuentra, 500 si hay error
     */
    @PATCH
    @Path("/{id}")
    public Response actualizarParcialUsuario(@PathParam("id") int id, Usuario datos) {
        try {
            // Buscar si el usuario existe
            Usuario existente = dao.buscarPorId(id);
            if (existente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\": \"Usuario no encontrado\"}")
                               .build();
            }

            // Solo se actualizan los campos no nulos enviados
            if (datos.getNombre() != null) {
                existente.setNombre(datos.getNombre());
            }

            if (datos.getCorreo() != null) {
                existente.setCorreo(datos.getCorreo());
            }

            if (datos.getContrasena() != null) {
                // Se encripta la nueva contraseña
                existente.setContrasena(encriptarSHA256(datos.getContrasena()));
            }

            if (datos.getId_rol() != 0) {
                existente.setId_rol(datos.getId_rol());
            }

            if (datos.getEstado() != null) {
                existente.setEstado(datos.getEstado());
            }

            // Se realiza la actualización con los nuevos valores parciales
            boolean actualizado = dao.actualizar(existente);

            if (actualizado) {
                return Response.ok("{\"mensaje\": \"Usuario actualizado parcialmente\"}").build();
            } else {
                return Response.status(500)
                               .entity("{\"error\": \"No se pudo aplicar la actualización parcial\"}")
                               .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        }
    }

}
