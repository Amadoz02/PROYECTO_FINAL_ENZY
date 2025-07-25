package modelo; // Indica que esta clase está en el paquete llamado "modelo"

import controlador.Utilidades; // Importa una clase de utilidades, por ejemplo, para encriptar contraseñas
import controlador.Conexion;   // Importa la clase que se encarga de conectarse a la base de datos
import java.sql.*;       // Importa todo lo necesario para trabajar con bases de datos SQL
import java.util.*;      // Importa clases útiles como List y ArrayList

/**
 * Esta clase se encarga de manejar todo lo relacionado con los usuarios en la base de datos.
 * Aquí se hacen operaciones como listar, buscar por ID, insertar nuevos usuarios,
 * actualizar información y eliminar usuarios.
 */
public class UsuarioDAO {

    /**
     * Este método devuelve una lista con todos los usuarios que hay en la base de datos.
     */
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>(); // Crea una lista vacía para guardar los usuarios encontrados

        try (
            Connection conn = Conexion.getConexion(); // Intenta abrir una conexión a la base de datos
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuarios"); // Prepara la consulta SQL
            ResultSet rs = ps.executeQuery() // Ejecuta la consulta y obtiene los resultados
        ) {
            while (rs.next()) { // Mientras haya resultados disponibles...
                Usuario u = new Usuario(); // Crea un nuevo objeto de tipo Usuario

                u.setId_usuario(rs.getInt("id_usuario")); // Obtiene el ID del usuario desde la base de datos
                u.setNombre(rs.getString("nombre")); // Obtiene el nombre del usuario
                u.setCorreo(rs.getString("correo")); // Obtiene el correo del usuario
                u.setContrasena(null); // Por seguridad, no se devuelve la contraseña
                u.setId_rol(rs.getInt("id_rol")); // Obtiene el rol del usuario
                u.setEstado(rs.getString("estado")); // Obtiene el estado del usuario (activo/inactivo)

                lista.add(u); // Agrega el usuario a la lista
            }

        } catch (Exception e) {
            e.printStackTrace(); // Muestra el error en consola si algo falla
        }

        return lista; // Devuelve la lista de usuarios
    }

    /**
     * Este método busca un usuario por su ID.
     * @param id El ID del usuario que se quiere buscar.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuario buscarPorId(int id) {
        Usuario u = null; // Prepara un usuario vacío

        try (
            Connection conn = Conexion.getConexion(); // Abre la conexión
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuarios WHERE id_usuario = ?") // Prepara la consulta
        ) {
            ps.setInt(1, id); // Reemplaza el "?" de la consulta con el ID que se recibió
            ResultSet rs = ps.executeQuery(); // Ejecuta la consulta

            if (rs.next()) { // Si encontró un resultado...
                u = new Usuario(); // Crea un objeto Usuario
                u.setId_usuario(rs.getInt("id_usuario")); // Llena los datos del usuario
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(null); // No se devuelve la contraseña por seguridad
                u.setId_rol(rs.getInt("id_rol"));
                u.setEstado(rs.getString("estado"));
            }

        } catch (Exception e) {
            e.printStackTrace(); // Imprime cualquier error
        }

        return u; // Devuelve el usuario encontrado (o null)
    }

 /**
 * Este método guarda un nuevo usuario en la base de datos.
 * @param u El objeto Usuario que se desea guardar.
 * @return El ID del usuario creado si se guardó correctamente, o -1 si falló.
     * @throws java.lang.Exception
 */
public int insertar(Usuario u) throws Exception {
    String sql = "INSERT INTO usuarios(nombre, correo, contrasena, id_rol, estado) VALUES (?, ?, ?, ?, ?)";
    int idUsuarioCreado = -1; // Inicializa el ID del usuario creado

    try (
        Connection con = Conexion.getConexion(); // Abre la conexión
            
        PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) // Prepara la consulta SQL y permite obtener el ID generado
    ) {
        
        stmt.setString(1, u.getNombre()); // Coloca el nombre en la consulta
        stmt.setString(2, u.getCorreo()); // Coloca el correo
        stmt.setString(3, u.getContrasena()); // Coloca la contraseña (debe estar ya encriptada antes)
        stmt.setInt(4, u.getId_rol()); // Coloca el ID del rol
        stmt.setString(5, u.getEstado()); // Coloca el estado del usuario

        stmt.executeUpdate(); // Ejecuta la consulta para guardar el usuario

        // Obtiene el ID generado
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                idUsuarioCreado = generatedKeys.getInt(1); // Obtiene el ID del usuario creado
            }
        }

    } catch (SQLException e) {
        e.printStackTrace(); // Muestra el error si ocurre algo
    }

    return idUsuarioCreado; // Devuelve el ID del usuario creado o -1 si falló
}


    /**
     * Este método permite actualizar los datos de un usuario que ya existe.
     * @param u El objeto Usuario con los nuevos datos.
     * @return true si se actualizó bien, false si falló.
     */
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre = ?, correo = ?, contrasena = ?, id_rol = ?, estado = ? WHERE id_usuario = ?";

        try (
            Connection conn = Conexion.getConexion(); // Abre la conexión
            PreparedStatement ps = conn.prepareStatement(sql) // Prepara la consulta
        ) {
            ps.setString(1, u.getNombre()); // Establece el nuevo nombre
            ps.setString(2, u.getCorreo()); // Establece el nuevo correo
            ps.setString(3, Utilidades.encriptarSHA256(u.getContrasena())); // Encripta la nueva contraseña antes de guardarla
            ps.setInt(4, u.getId_rol()); // Establece el nuevo rol
            ps.setString(5, u.getEstado()); // Establece el nuevo estado
            ps.setInt(6, u.getId_usuario()); // Indica a qué usuario se le harán los cambios

            return ps.executeUpdate() > 0; // Devuelve true si se actualizó al menos 1 fila

        } catch (Exception e) {
            e.printStackTrace(); // Muestra cualquier error
            return false; // Indica fallo
        }
    }

    /**
     * Este método elimina a un usuario por su ID.
     * @param id El ID del usuario que se desea borrar.
     * @return true si se borró, false si no.
     */
    public boolean eliminar(int id) {
        try (
            Connection conn = Conexion.getConexion(); // Abre conexión
            PreparedStatement ps = conn.prepareStatement("DELETE FROM usuarios WHERE id_usuario = ?") // Prepara la consulta
        ) {
            ps.setInt(1, id); // Coloca el ID del usuario a borrar
            return ps.executeUpdate() > 0; // Devuelve true si se borró al menos 1 usuario

        } catch (Exception e) {
            e.printStackTrace(); // Muestra error
            return false; // Indica que hubo un fallo
        }
    }
}
