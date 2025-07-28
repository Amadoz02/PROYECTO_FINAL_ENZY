package modelo;

import java.sql.*;
import java.util.*;


public class MetodoPagoDAO {
    private Connection con;

    public MetodoPagoDAO(Connection con) {
        this.con = con;
    }

    public List<MetodoPago> obtenerTodos() throws SQLException {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM metodos_pago";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            MetodoPago m = new MetodoPago();
            m.setId_metodo(rs.getInt("id_metodo"));
            m.setNombre(rs.getString("nombre"));
            lista.add(m);
        }

        return lista;
    }

    public void insertar(MetodoPago m) throws SQLException {
        String sql = "INSERT INTO metodos_pago (nombre) VALUES (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, m.getNombre());
        ps.executeUpdate();
    }

    public void actualizar(MetodoPago m) throws SQLException {
        String sql = "UPDATE metodos_pago SET nombre=? WHERE id_metodo=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, m.getNombre());
        ps.setInt(2, m.getId_metodo());
        ps.executeUpdate();
    }

    public void eliminar(int id_metodo) throws SQLException {
        String sql = "DELETE FROM metodos_pago WHERE id_metodo=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id_metodo);
        ps.executeUpdate();
    }
}
