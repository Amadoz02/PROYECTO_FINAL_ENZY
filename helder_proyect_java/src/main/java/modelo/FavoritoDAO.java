package modelo;

import java.sql.*;
import java.util.*;
import modelo.Favorito;
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

    // Obtener favorito por ID
    public Favorito obtenerPorId(int idProducto) {
        String sql = "SELECT * FROM favoritos WHERE id_producto = ?";
        Favorito f = null;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                f = new Favorito();
                f.setIdUsuario(rs.getInt("id_usuario"));
                f.setIdProducto(rs.getInt("id_producto"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }

    // Insertar un nuevo favorito
    public boolean insertar(Favorito f) {
        String sql = "INSERT INTO favoritos (id_usuario, id_producto) VALUES (?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, f.getIdUsuario());
            ps.setInt(2, f.getIdProducto());
            return ps.executeUpdate() > 0;

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


    // Listar favoritos por ID de producto
    public List<Favorito> listarPorIdProducto(int idProducto) {
        List<Favorito> lista = new ArrayList<>();
        String sql = "SELECT * FROM favoritos WHERE id_producto = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
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
}
