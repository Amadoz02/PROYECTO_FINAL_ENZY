package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import controlador.Conexion;

/**
 * Clase DAO (Data Access Object) para la entidad Rol.
 * Permite realizar operaciones de base de datos sobre la tabla 'roles'.
 */
public class RolDAO {

    /**
     * Método para obtener todos los roles registrados en la base de datos.
     * @return Lista de objetos Rol
     */
    public List<Rol> obtenerTodos() throws Exception {
        List<Rol> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Obtener conexión desde la clase utilitaria
            conn = Conexion.getConexion();

            // Consulta SQL para obtener todos los roles
            String sql = "SELECT * FROM roles";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Recorremos los resultados y los agregamos a la lista
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setId_rol(rs.getInt("id_rol"));
                rol.setNombre_rol(rs.getString("nombre_rol"));
                lista.add(rol);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener roles: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Método para obtener un solo rol por su ID.
     * @param id Identificador del rol
     * @return Objeto Rol correspondiente, o null si no se encuentra
     */
    public Rol obtenerPorId(int id) throws Exception {
        Rol rol = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion();
            String sql = "SELECT * FROM roles WHERE id_rol = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                rol = new Rol();
                rol.setId_rol(rs.getInt("id_rol"));
                rol.setNombre_rol(rs.getString("nombre_rol"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener rol por ID: " + e.getMessage());
        }

        return rol;
    }

    /**
     * Método para registrar un nuevo rol en la base de datos.
     * @param rol Objeto Rol a registrar
     * @return true si se registró correctamente, false si hubo error
     */
    public boolean insertar(Rol rol) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean resultado = false;

        try {
            conn = Conexion.getConexion();
            String sql = "INSERT INTO roles (nombre_rol) VALUES (?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, rol.getNombre_rol());

            resultado = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar rol: " + e.getMessage());
        } 

        return resultado;
    }

    /**
     * Método para actualizar un rol existente.
     * @param rol Objeto Rol con los nuevos datos
     * @return true si se actualizó correctamente, false si hubo error
     */
    public boolean actualizar(Rol rol) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean resultado = false;

        try {
            conn = Conexion.getConexion();
            String sql = "UPDATE roles SET nombre_rol = ? WHERE id_rol = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, rol.getNombre_rol());
            stmt.setInt(2, rol.getId_rol());

            resultado = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar rol: " + e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return resultado;
    }

    /**
     * Método para eliminar un rol por su ID.
     * @param id Identificador del rol a eliminar
     * @return true si se eliminó correctamente, false si hubo error
     */
    public boolean eliminar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean resultado = false;

        try {
            conn = Conexion.getConexion();
            String sql = "DELETE FROM roles WHERE id_rol = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            resultado = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar rol: " + e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return resultado;
    }
}
