package modelo;

import java.sql.*;
import java.util.*;
import static controlador.Conexion.getConexion;

public class ProductoDAO {

    public List<Producto> obtenerTodos() throws Exception{
        Connection con = getConexion();
        String sql = "SELECT * FROM productos";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Producto> lista = new ArrayList<>();
        while (rs.next()) {
            Producto p = new Producto();
            p.setId_producto(rs.getInt("id_producto"));
            p.setNombre(rs.getString("nombre"));
            p.setPrecio(rs.getDouble("precio"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setId_talla(rs.getInt("id_talla"));
            p.setId_categoria(rs.getInt("id_categoria"));
            p.setId_genero(rs.getInt("id_genero"));
            p.setEstado(rs.getString("estado"));
            lista.add(p);
        }

        con.close();
        return lista;
    }

    public Producto obtenerPorId(int id) throws Exception {
        Connection con = getConexion();
        String sql = "SELECT * FROM productos WHERE id_producto=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Producto p = null;
        if (rs.next()) {
            p = new Producto();
            p.setId_producto(rs.getInt("id_producto"));
            p.setNombre(rs.getString("nombre"));
            p.setPrecio(rs.getDouble("precio"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setId_talla(rs.getInt("id_talla"));
            p.setId_categoria(rs.getInt("id_categoria"));
            p.setId_genero(rs.getInt("id_genero"));
            p.setEstado(rs.getString("estado"));
        }

        con.close();
        return p;
    }

    public void insertar(Producto p) throws Exception {
        Connection con = getConexion();
        String sql = "INSERT INTO productos (nombre, precio, descripcion, id_talla, id_categoria, id_genero, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, p.getNombre());
        ps.setDouble(2, p.getPrecio());
        ps.setString(3, p.getDescripcion());
        ps.setInt(4, p.getId_talla());
        ps.setInt(5, p.getId_categoria());
        ps.setInt(6, p.getId_genero());
        ps.setString(7, p.getEstado());
        ps.executeUpdate();
        con.close();
    }

    public void actualizar(Producto p) throws Exception {
        Connection con = getConexion();
        String sql = "UPDATE productos SET nombre=?, precio=?, descripcion=?, id_talla=?, id_categoria=?, id_genero=?, estado=? WHERE id_producto=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, p.getNombre());
        ps.setDouble(2, p.getPrecio());
        ps.setString(3, p.getDescripcion());
        ps.setInt(4, p.getId_talla());
        ps.setInt(5, p.getId_categoria());
        ps.setInt(6, p.getId_genero());
        ps.setString(7, p.getEstado());
        ps.setInt(8, p.getId_producto());
        ps.executeUpdate();
        con.close();
    }

    public void eliminar(int id) throws Exception {
        Connection con = getConexion();
        String sql = "DELETE FROM productos WHERE id_producto=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
    }
}
