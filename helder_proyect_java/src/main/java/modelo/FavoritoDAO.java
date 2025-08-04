package modelo;

import java.sql.*;
import java.util.*;
import controlador.Conexion;

public class FavoritoDAO {

    // Obtener todos los favoritos
    public List<Favorito> obtenerTodos() {
        List<Favorito> lista = new ArrayList<>();
        String sql = "SELECT * FROM favoritos";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Favorito f = new Favorito();
                f.setIdUsuario(rs.getInt("id_usuario"));
                f.setIdProducto(rs.getInt("id_producto"));
                lista.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    public List<Favorito> obtenerPorIdUsuario(int idUsuario) {
        List<Favorito> lista = new ArrayList<>();
        String sql = "SELECT * FROM favoritos WHERE id_usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Favorito f = new Favorito();
                f.setIdUsuario(rs.getInt("id_usuario"));
                f.setIdProducto(rs.getInt("id_producto"));
                lista.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Insertar un nuevo favorito
    public boolean insertar(Favorito f) {
        try (Connection con = Conexion.getConexion()) {

            // Verifica si el usuario y producto existen
            if (!usuarioExiste(f.getIdUsuario()) || !productoExiste(f.getIdProducto())) {
                System.out.println("❌ Usuario o producto no existen.");
                return false;
            }

            // Verifica si ya existe el favorito
            String verificarSql = "SELECT COUNT(*) FROM favoritos WHERE id_usuario = ? AND id_producto = ?";
            try (PreparedStatement verificarStmt = con.prepareStatement(verificarSql)) {
                verificarStmt.setInt(1, f.getIdUsuario());
                verificarStmt.setInt(2, f.getIdProducto());

                ResultSet rs = verificarStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("⚠️ El producto ya está en favoritos.");
                    return false;
                }
            }

            // Inserta el favorito
            String sql = "INSERT INTO favoritos (id_usuario, id_producto) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, f.getIdUsuario());
                ps.setInt(2, f.getIdProducto());
                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }



    // Eliminar por ID
public boolean eliminarPorUsuarioYProducto(int id_usuario, int id_producto) throws Exception {
    String sql = "DELETE FROM favoritos WHERE id_usuario = ? AND id_producto = ?";
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id_usuario);
        ps.setInt(2, id_producto);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    // Listar favoritos por ID de producto y usuario
    public List<Favorito> listarPorId(int idProducto, int idUsuario) {
        List<Favorito> lista = new ArrayList<>();
        String sql = "SELECT * FROM favoritos WHERE id_producto = ? AND id_usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Favorito f = new Favorito();
                f.setIdUsuario(rs.getInt("id_usuario"));
                f.setIdProducto(rs.getInt("id_producto"));
                lista.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public boolean usuarioExiste(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE id_usuario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean productoExiste(int idProducto) {
        String sql = "SELECT COUNT(*) FROM productos WHERE id_producto = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
