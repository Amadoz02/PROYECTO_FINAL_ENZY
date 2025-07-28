/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import controlador.Conexion;
import java.sql.*;
import java.util.*;


public class ImagenDAO {

    public boolean insertar(Imagen img) throws Exception {
        String sql = "INSERT INTO imagenes (id_producto, url_imagen, descripcion) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, img.getId_producto());
            ps.setString(2, img.getUrl_imagen());
            ps.setString(3, img.getDescripcion());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Imagen> listar() throws Exception {
        List<Imagen> lista = new ArrayList<>();
        String sql = "SELECT * FROM imagenes";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Imagen img = new Imagen();
                img.setId_imagen(rs.getInt("id_imagen"));
                img.setId_producto(rs.getInt("id_producto"));
                img.setUrl_imagen(rs.getString("url_imagen"));
                img.setDescripcion(rs.getString("descripcion"));
                lista.add(img);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    // Método para listar todas las imágenes asociadas a un ID de producto específico
    public List<Imagen> listarByProductoId(int id_producto) throws Exception {
        List<Imagen> lista = new ArrayList<>();

        // Consulta SQL para filtrar imágenes por id_producto
        String sql = "SELECT * FROM imagenes WHERE id_producto = ?";

        // Establecemos conexión, preparamos la consulta y asignamos el parámetro
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Se establece el valor del parámetro id_producto en la consulta
            ps.setInt(1, id_producto);

            // Ejecutamos la consulta después de haber seteado los parámetros
            try (ResultSet rs = ps.executeQuery()) {
                // Iteramos los resultados y los agregamos a la lista
                while (rs.next()) {
                    Imagen img = new Imagen();
                    img.setId_imagen(rs.getInt("id_imagen"));
                    img.setId_producto(rs.getInt("id_producto"));
                    img.setUrl_imagen(rs.getString("url_imagen"));
                    img.setDescripcion(rs.getString("descripcion"));
                    lista.add(img);
                }
            }
        } catch (SQLException e) {
            // Se imprime el error y se lanza de nuevo para que lo capture quien lo llame
            e.printStackTrace();
            throw new Exception("Error al listar imágenes por producto", e);
        }

        return lista;
    }

    
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM imagenes WHERE id_imagen = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Imagen img) throws Exception {
        String sql = "UPDATE imagenes SET id_producto=?, url_imagen=?, descripcion=? WHERE id_imagen=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, img.getId_producto());
            ps.setString(2, img.getUrl_imagen());
            ps.setString(3, img.getDescripcion());
            ps.setInt(4, img.getId_imagen());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
