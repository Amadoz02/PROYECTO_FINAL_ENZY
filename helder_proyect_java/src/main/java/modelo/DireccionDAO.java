package modelo;

import controlador.Conexion; // Importamos la clase de conexión a la base de datos
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de todas las operaciones de base de datos
 * relacionadas con la tabla 'direcciones'.
 * Aquí se puede listar, buscar, insertar, actualizar y eliminar direcciones.
 */
public class DireccionDAO {

    /**
     * Método para obtener todas las direcciones guardadas en la base de datos.
     * 
     * @return una lista de objetos Direccion
     */
    public List<Direccion> listarDirecciones() {
        List<Direccion> lista = new ArrayList<>(); // Lista donde se guardarán las direcciones encontradas

        try (Connection conn = Conexion.getConexion(); // Conectamos a la base de datos
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM direcciones"); // Consulta SQL
             ResultSet rs = ps.executeQuery()) { // Ejecutamos la consulta y obtenemos los resultados

            // Mientras haya resultados
            while (rs.next()) {
                Direccion d = new Direccion(); // Creamos un nuevo objeto de tipo Direccion
                d.setId_direccion(rs.getInt("id_direccion")); // Asignamos el ID
                d.setId_usuario(rs.getInt("id_usuario")); // Asignamos el ID de usuario
                d.setDireccion(rs.getString("direccion")); // Asignamos la dirección
                d.setCiudad(rs.getString("ciudad")); // Asignamos la ciudad
                d.setDepartamento(rs.getString("departamento")); // Asignamos el departamento
                d.setCodigo_postal(rs.getString("codigo_postal")); // Asignamos el código postal
                d.setObservaciones(rs.getString("observaciones")); // Asignamos las observaciones
                lista.add(d); // Añadimos la dirección a la lista
            }

        } catch (Exception e) {
            e.printStackTrace(); // Mostramos errores si ocurren
        }

        return lista; // Retornamos la lista final
    }

    /**
     * Método para buscar una dirección por su ID.
     * 
     * @param id ID de la dirección a buscar
     * @return Objeto Direccion si se encuentra, null si no
     */
    public Direccion buscarPorId(int id) {
        Direccion d = null;

        try (Connection conn = Conexion.getConexion(); // Abrimos la conexión
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM direcciones WHERE id_direccion = ?")) {

            ps.setInt(1, id); // Asignamos el valor del parámetro al query
            ResultSet rs = ps.executeQuery(); // Ejecutamos la consulta

            if (rs.next()) { // Si se encuentra un resultado
                d = new Direccion(); // Creamos el objeto
                d.setId_direccion(rs.getInt("id_direccion"));
                d.setId_usuario(rs.getInt("id_usuario"));
                d.setDireccion(rs.getString("direccion"));
                d.setCiudad(rs.getString("ciudad"));
                d.setDepartamento(rs.getString("departamento"));
                d.setCodigo_postal(rs.getString("codigo_postal"));
                d.setObservaciones(rs.getString("observaciones"));
            }

        } catch (Exception e) {
            e.printStackTrace(); // Mostramos error si ocurre
        }

        return d; // Retornamos la dirección encontrada (o null si no se encontró)
    }

    /**
     * Método para insertar una nueva dirección en la base de datos.
     * 
     * @param d Objeto Direccion con los datos a insertar
     * @return true si se insertó correctamente, false si ocurrió algún error
     */
    public boolean insertar(Direccion d) {
        String sql = "INSERT INTO direcciones (id_usuario, direccion, ciudad, departamento, codigo_postal, observaciones) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getId_usuario()); // Asignamos el ID de usuario
            ps.setString(2, d.getDireccion()); // Asignamos la dirección
            ps.setString(3, d.getCiudad()); // Asignamos la ciudad
            ps.setString(4, d.getDepartamento()); // Asignamos el departamento
            ps.setString(5, d.getCodigo_postal()); // Asignamos el código postal
            ps.setString(6, d.getObservaciones()); // Asignamos observaciones

            return ps.executeUpdate() > 0; // Retornamos true si se insertó correctamente

        } catch (Exception e) {
            e.printStackTrace(); // Mostrar error en consola
            return false; // Si falla, retornamos false
        }
    }

    /**
     * Método para actualizar una dirección existente.
     * 
     * @param d Objeto Direccion con los datos actualizados
     * @return true si se actualizó correctamente, false si ocurrió un error
     */
    public boolean actualizar(Direccion d) {
        String sql = "UPDATE direcciones SET id_usuario=?, direccion=?, ciudad=?, departamento=?, codigo_postal=?, observaciones=? WHERE id_direccion=?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getId_usuario());
            ps.setString(2, d.getDireccion());
            ps.setString(3, d.getCiudad());
            ps.setString(4, d.getDepartamento());
            ps.setString(5, d.getCodigo_postal());
            ps.setString(6, d.getObservaciones());
            ps.setInt(7, d.getId_direccion());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace(); // Mostrar error
            return false;
        }
    }

    /**
     * Método para eliminar una dirección por su ID.
     * 
     * @param id ID de la dirección a eliminar
     * @return true si se eliminó correctamente, false si ocurrió un error
     */
    public boolean eliminar(int id) {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM direcciones WHERE id_direccion = ?")) {

            ps.setInt(1, id); // Asignamos el ID
            return ps.executeUpdate() > 0; // Retorna true si se eliminó

        } catch (Exception e) {
            e.printStackTrace(); // Mostrar error
            return false;
        }
    }
}
