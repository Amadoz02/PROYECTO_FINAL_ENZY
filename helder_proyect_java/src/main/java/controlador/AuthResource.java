package controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.json.*;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String jsonData) {
        System.out.println("Recibiendo petición de login...");

        try (JsonReader reader = Json.createReader(new StringReader(jsonData))) {
            JsonObject jsonObject = reader.readObject();

            if (!jsonObject.containsKey("correo") || !jsonObject.containsKey("contrasena")) {
                System.out.println("Faltan campos en el JSON.");
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Json.createObjectBuilder()
                                .add("error", "Faltan campos obligatorios.")
                                .build()
                                .toString())
                        .build();
            }

            String correo = jsonObject.getString("correo");
            String contrasena = jsonObject.getString("contrasena");

            try (Connection conn = Conexion.getConexion()) {
                String sql = """
                    SELECT u.id_usuario, u.contrasena, r.nombre_rol AS rol
                    FROM usuarios u
                    JOIN roles r ON u.id_rol = r.id_rol
                    WHERE u.correo = ?
                """;

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, correo);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            String passBD = rs.getString("contrasena");
                            int idUsuario = rs.getInt("id_usuario");
                            String rol = rs.getString("rol");

                            if (contrasena.equals(passBD)) {
                                String accessToken = JwtUtil.generarAccessToken(idUsuario, rol);
                                String refreshToken = JwtUtil.generarRefreshToken(idUsuario);

                                JsonObject response = Json.createObjectBuilder()
                                        .add("accessToken", accessToken)
                                        .add("refreshToken", refreshToken)
                                        .add("rol", rol)
                                        .add("id_usuario", idUsuario) // ✅ Agregado aquí
                                        .build();

                                return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();

                            } else {
                                return Response.status(Response.Status.UNAUTHORIZED)
                                        .entity(Json.createObjectBuilder()
                                                .add("error", "Contraseña incorrecta.")
                                                .build()
                                                .toString())
                                        .build();
                            }
                        } else {
                            return Response.status(Response.Status.NOT_FOUND)
                                    .entity(Json.createObjectBuilder()
                                            .add("error", "Usuario no encontrado.")
                                            .build()
                                            .toString())
                                    .build();
                        }
                    }
                }
           } catch (Exception e) {
    e.printStackTrace(); // Para consola
    // Para frontend, enviamos el mensaje real (solo mientras estás en desarrollo)
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(Json.createObjectBuilder()
                    .add("error", "Error BD: " + e.getMessage())
                    .build()
                    .toString())
            .build();
}

        } catch (Exception e) {
            e.printStackTrace(); // Log exacto del error
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Json.createObjectBuilder()
                            .add("error", "Error procesando la solicitud.")
                            .build()
                            .toString())
                    .build();
        }
    }
}
