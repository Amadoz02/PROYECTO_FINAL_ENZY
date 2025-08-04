package controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import modelo.Usuario;
import modelo.UsuarioDAO;
import static controlador.Utilidades.encriptarSHA256;


@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private UsuarioDAO dao = new UsuarioDAO();

    @GET
    public Response getUsuarios() {
        try {
            List<Usuario> lista = dao.listarUsuarios();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\": \"Error al listar usuarios\"}")
                           .build();
        }
    }

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

    @POST
    public Response agregarUsuario(Usuario usuario) {
        try {
            // Validaciones
            Validador.validar(usuario); // Validaciones de Bean Validation
            Validador.validarEmail(usuario.getCorreo()); // Validación de email
            Validador.validarLongitudCampo(usuario.getNombre(), "Nombre", 2, 50); // Validación de longitud
            Validador.validarLongitudCampo(usuario.getContrasena(), "contrasena", 6, 18);
            // Encriptar la contraseña
            usuario.setContrasena(encriptarSHA256(usuario.getContrasena()));

            int idUsuarioCreado = dao.insertar(usuario);
            if (idUsuarioCreado > 0) {
                return Response.status(Response.Status.CREATED)
                               .entity("{\"id_usuario\": " + idUsuarioCreado + ", \"mensaje\": \"Usuario creado correctamente\"}")
                               .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("{\"error\": \"No se pudo registrar el usuario\"}")
                               .build();
            }
        } catch (CustomException e) {
            return Response.status(e.getCodigoError())
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizarUsuario(@PathParam("id") int id, Usuario usuario) {
        try {
            Usuario existente = dao.buscarPorId(id);
            if (existente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\": \"Usuario no encontrado\"}")
                               .build();
            }

            // Validaciones
            Validador.validar(usuario); // Validaciones de Bean Validation
            Validador.validarEmail(usuario.getCorreo()); // Validación de email
            Validador.validarLongitudCampo(usuario.getNombre(), "Nombre", 2, 50); // Validación de longitud

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
        } catch (CustomException e) {
            return Response.status(e.getCodigoError())
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        }
    }

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

    @PATCH
    @Path("/{id}")
    public Response actualizarParcialUsuario(@PathParam("id") int id, Usuario datos) {
        try {
            Usuario existente = dao.buscarPorId(id);
            if (existente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"error\": \"Usuario no encontrado\"}")
                               .build();
            }

            // Solo se actualizan los campos no nulos enviados
            if (datos.getNombre() != null) {
                Validador.validarLongitudCampo(datos.getNombre(), "Nombre", 2, 50); // Validación de longitud
                existente.setNombre(datos.getNombre());
            }

            if (datos.getCorreo() != null) {
                Validador.validarEmail(datos.getCorreo()); // Validación de email
                existente.setCorreo(datos.getCorreo());
            }

            if (datos.getContrasena() != null) {
                existente.setContrasena(encriptarSHA256(datos.getContrasena()));
            }

            if (datos.getId_rol() != 0) {
                existente.setId_rol(datos.getId_rol());
            }

            if (datos.getEstado() != null) {
                existente.setEstado(datos.getEstado());
            }

            boolean actualizado = dao.actualizar(existente);
            if (actualizado) {
                return Response.ok("{\"mensaje\": \"Usuario actualizado parcialmente\"}").build();
            } else {
                return Response.status(500)
                               .entity("{\"error\": \"No se pudo aplicar la actualización parcial\"}")
                               .build();
            }
        } catch (CustomException e) {
            return Response.status(e.getCodigoError())
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        }
    }
}
