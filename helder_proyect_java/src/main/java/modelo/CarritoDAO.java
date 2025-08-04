package modelo;

import java.sql.*;
import java.util.*;
import controlador.Conexion;

/**
 * Clase CarritoDAO
 * Realiza operaciones CRUD para los carritos de compra.
 */
public class CarritoDAO {

    private Connection con;

    // Constructor que recibe conexión
    public CarritoDAO(Connection con) {
        this.con = con;
    }
    
    public Carrito buscarCarritoActivoPorUsuario(int idUsuario) throws SQLException {
    String sql = "SELECT * FROM carritos WHERE id_cliente = ? AND estado = 'activo'";
    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            Carrito c = new Carrito();
            c.setId_carrito(rs.getInt("id_carrito"));
            c.setId_usuario(rs.getInt("id_cliente"));
            c.setEstado(rs.getString("estado"));
            return c;
        }
        return null;
    }
}
    
    // Obtener todos los carritos
    public List<Carrito> obtenerTodos() throws SQLException {
        List<Carrito> lista = new ArrayList<>();
        String sql = "SELECT * FROM carritos";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Carrito c = new Carrito();
            c.setId_carrito(rs.getInt("id_carrito"));
            c.setId_usuario(rs.getInt("id_cliente"));           
            c.setEstado(rs.getString("estado"));

            lista.add(c);
        }

        return lista;
    }

    // Insertar nuevo carrito
    public void insertar(Carrito carrito) throws SQLException {
        String sql = "INSERT INTO carritos (id_cliente, estado) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, carrito.getId_usuario());  // Usa id_cliente
            stmt.setString(2, carrito.getEstado());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    carrito.setId_carrito(rs.getInt(1)); // Guardar el id generado en el objeto
                }
            }
        }
    }


    // Actualizar carrito
    public void actualizar(Carrito c) throws SQLException {
        String sql = "UPDATE carritos SET id_cliente = ?, estado = ? WHERE id_carrito = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, c.getId_usuario());
        stmt.setInt(3, c.getId_carrito());
        stmt.setString(4, c.getEstado());
        stmt.executeUpdate();
    }

    // Eliminar carrito por ID
    public void eliminar(int id_carrito) throws SQLException {
        String sql = "DELETE FROM carritos WHERE id_carrito = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id_carrito);
        stmt.executeUpdate();
    }

}
