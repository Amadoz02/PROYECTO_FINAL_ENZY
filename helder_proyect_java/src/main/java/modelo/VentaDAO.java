package modelo;

import controlador.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    // Crear una nueva venta
    public boolean crearVenta(Venta venta) throws Exception {
        String sql = "INSERT INTO ventas (id_usuario, total, id_metodo, estado, id_carrito) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venta.getId_usuario());
            stmt.setDouble(2, venta.getTotal());
            stmt.setInt(3, venta.getId_metodo());
            stmt.setString(4, venta.getEstado());
            stmt.setInt(5, venta.getId_carrito());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todas las ventas
    public List<Venta> obtenerVentas() throws Exception {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ventas";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId_venta(rs.getInt("id_venta"));
                venta.setId_usuario(rs.getInt("id_usuario"));
                venta.setFecha(rs.getTimestamp("fecha"));
                venta.setTotal(rs.getDouble("total"));
                venta.setId_metodo(rs.getInt("id_metodo"));
                venta.setEstado(rs.getString("estado"));
                venta.setId_carrito(rs.getInt("id_carrito"));

                lista.add(venta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Obtener una venta por ID
    public Venta obtenerVentaPorId(int id) throws Exception {
        String sql = "SELECT * FROM ventas WHERE id_venta = ?";
        Venta venta = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                venta = new Venta();
                venta.setId_venta(rs.getInt("id_venta"));
                venta.setId_usuario(rs.getInt("id_usuario"));
                venta.setFecha(rs.getTimestamp("fecha"));
                venta.setTotal(rs.getDouble("total"));
                venta.setId_metodo(rs.getInt("id_metodo"));
                venta.setEstado(rs.getString("estado"));
                venta.setId_carrito(rs.getInt("id_carrito"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return venta;
    }

    // Actualizar una venta existente
    public boolean actualizarVenta(Venta venta) throws Exception {
        String sql = "UPDATE ventas SET id_usuario = ?, total = ?, id_metodo = ?, estado = ?, id_carrito = ? WHERE id_venta = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venta.getId_usuario());
            stmt.setDouble(2, venta.getTotal());
            stmt.setInt(3, venta.getId_metodo());
            stmt.setString(4, venta.getEstado());
            stmt.setInt(5, venta.getId_carrito());
            stmt.setInt(6, venta.getId_venta());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar una venta
    public boolean eliminarVenta(int id) throws Exception {
        String sql = "DELETE FROM ventas WHERE id_venta = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
