package modelo;
import controlador.Conexion;
import java.sql.*;
import java.util.*;


public class GeneroDAO {

    // Insertar nuevo género
    public void insertar(Genero genero) throws Exception {
        String sql = "INSERT INTO generos (tipo_genero) VALUES (?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, genero.getTipo_genero());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar todos los géneros
    public List<Genero> listar() throws Exception {
        List<Genero> lista = new ArrayList<>();
        String sql = "SELECT * FROM generos";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Genero g = new Genero();
                g.setId_genero(rs.getInt("id_genero"));
                g.setTipo_genero(rs.getString("tipo_genero"));
                lista.add(g);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar un género
    public void actualizar(Genero genero) throws Exception {
        String sql = "UPDATE generos SET tipo_genero = ? WHERE id_genero = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, genero.getTipo_genero());
            stmt.setInt(2, genero.getId_genero());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un género por ID
    public void eliminar(int id_genero) throws Exception {
        String sql = "DELETE FROM generos WHERE id_genero = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_genero);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar un género por ID
    public Genero buscarPorId(int id_genero) throws Exception {
        String sql = "SELECT * FROM generos WHERE id_genero = ?";
        Genero genero = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_genero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    genero = new Genero();
                    genero.setId_genero(rs.getInt("id_genero"));
                    genero.setTipo_genero(rs.getString("tipo_genero"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genero;
    }
}
