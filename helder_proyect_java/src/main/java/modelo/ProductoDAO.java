package modelo;

import controlador.Conexion;
import java.sql.*;
import java.util.*;
import static controlador.Conexion.getConexion;

public class ProductoDAO {

   public List<Producto> obtenerTodos() throws Exception {
    List<Producto> lista = new ArrayList<>();

    // Consulta SQL que junta productos con tallas, categorías y géneros
    String sql = """
        SELECT 
          p.id_producto,
          p.nombre,
          p.precio,
          p.descripcion,
          p.estado,
          p.id_talla,
          p.id_categoria,
          p.id_genero,
          t.talla,
          c.nombre AS categoria,
          g.tipo_genero AS genero
        FROM productos p
        LEFT JOIN tallas t ON p.id_talla = t.id_talla
        LEFT JOIN categorias c ON p.id_categoria = c.id_categoria
        LEFT JOIN generos g ON p.id_genero = g.id_genero
    """;

    // Establecemos conexión y ejecutamos la consulta
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        // Creamos instancia del DAO que lista imágenes por producto
        ImagenDAO imagenDAO = new ImagenDAO();

        while (rs.next()) {
            Producto p = new Producto();

            // Asignación de datos básicos del producto
            p.setId_producto(rs.getInt("id_producto"));
            p.setNombre(rs.getString("nombre"));
            p.setPrecio(rs.getDouble("precio"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setEstado(rs.getString("estado"));

            // FK: IDs de relaciones
            p.setId_talla(rs.getInt("id_talla"));
            p.setId_categoria(rs.getInt("id_categoria"));
            p.setId_genero(rs.getInt("id_genero"));

            // Datos legibles de las relaciones
            p.setTalla(rs.getString("talla"));
            p.setCategoria(rs.getString("categoria"));
            p.setGenero(rs.getString("genero"));

            // 🔗 Aquí agregamos la lista de imágenes por producto
            List<Imagen> imagenes = imagenDAO.listarByProductoId(p.getId_producto());
            p.setImagenes(imagenes); // ← Esto asume que tienes un método setImagenes en Producto

            lista.add(p);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        throw new Exception("Error al obtener productos con imágenes", e);
    }

    return lista;
}


    public Producto obtenerPorId(int id) throws Exception {
        Connection con = getConexion();
        String sql = """
            SELECT 
                p.id_producto,
                p.nombre,
                p.precio,
                p.descripcion,
                p.estado,
                p.id_talla,
                p.id_categoria,
                p.id_genero,
                t.talla,
                c.nombre AS categoria,
                g.tipo_genero AS genero
            FROM productos p
            LEFT JOIN tallas t ON p.id_talla = t.id_talla
            LEFT JOIN categorias c ON p.id_categoria = c.id_categoria
            LEFT JOIN generos g ON p.id_genero = g.id_genero
            WHERE p.id_producto = ?
        """;

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
            p.setEstado(rs.getString("estado"));
            p.setId_talla(rs.getInt("id_talla"));
            p.setId_categoria(rs.getInt("id_categoria"));
            p.setId_genero(rs.getInt("id_genero"));
            p.setTalla(rs.getString("talla"));
            p.setCategoria(rs.getString("categoria"));
            p.setGenero(rs.getString("genero"));

            // Obtener y asignar lista de imágenes al producto
            ImagenDAO imagenDAO = new ImagenDAO();
            List<Imagen> imagenes = imagenDAO.listarByProductoId(id); // Asegúrate de que este método exista
            p.setImagenes(imagenes);
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
