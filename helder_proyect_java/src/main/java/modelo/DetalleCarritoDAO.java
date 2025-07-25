package modelo;

import controlador.Conexion; // Importa la clase de conexión a la base de datos
import java.sql.*; // Importa las clases necesarias para trabajar con SQL
import java.util.ArrayList; // Importa la clase ArrayList para manejar listas
import java.util.List; // Importa la interfaz List

/**
 * Clase DAO para manejar operaciones CRUD sobre la tabla detalles_carrito.
 */
public class DetalleCarritoDAO {

    private Connection con; // Conexión a la base de datos

    /**
     * Constructor que recibe la conexión ya creada.
     * @param con conexión activa a la base de datos.
     */
    public DetalleCarritoDAO(Connection con) {
        this.con = con; // Inicializa la conexión
    }

    /**
     * Inserta un nuevo detalle en la tabla detalles_carrito.
     * @param detalle el objeto DetalleCarrito a insertar.
     * @throws SQLException si ocurre un error de base de datos.
     */
    public void insertar(DetalleCarrito detalle) throws SQLException {
        String sql = "INSERT INTO detalles_carrito (id_carrito, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)"; // Consulta SQL para insertar
        try (PreparedStatement stmt = con.prepareStatement(sql)) { // Prepara la consulta
            stmt.setInt(1, detalle.getId_carrito()); // Asigna el ID del carrito
            stmt.setInt(2, detalle.getId_producto()); // Asigna el ID del producto
            stmt.setInt(3, detalle.getCantidad()); // Asigna la cantidad
            stmt.setDouble(4, detalle.getSubtotal()); // Asigna el subtotal
            stmt.executeUpdate(); // Ejecuta el INSERT
        } catch (SQLException e) {
            // Mensaje de error personalizado para problemas de SQL
            System.err.println("Error al insertar el detalle del carrito: " + e.getMessage());
            throw e; // Vuelve a lanzar la excepción para que el llamador la maneje
        }
    }

    /**
     * Devuelve una lista de todos los detalles de carrito en la base de datos.
     * @return lista de objetos DetalleCarrito.
     * @throws SQLException si ocurre un error al consultar.
     */
    public List<DetalleCarrito> obtenerTodos() throws SQLException {
        List<DetalleCarrito> lista = new ArrayList<>(); // Lista para almacenar los detalles del carrito
        String sql = "SELECT * FROM detalles_carrito"; // Consulta SQL para seleccionar todos los detalles
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) { // Crea el Statement y ejecuta la consulta
            // Itera sobre los resultados y crea objetos DetalleCarrito
            while (rs.next()) {
                DetalleCarrito d = new DetalleCarrito(); // Crea una nueva instancia de DetalleCarrito
                d.setId_detalle(rs.getInt("id_detalle")); // Asigna el ID del detalle
                d.setId_carrito(rs.getInt("id_carrito")); // Asigna el ID del carrito
                d.setId_producto(rs.getInt("id_producto")); // Asigna el ID del producto
                d.setCantidad(rs.getInt("cantidad")); // Asigna la cantidad
                d.setSubtotal(rs.getDouble("subtotal")); // Asigna el subtotal
                lista.add(d); // Se agrega a la lista final
            }
        } catch (SQLException e) {
            // Mensaje de error personalizado para problemas de SQL
            System.err.println("Error al obtener los detalles del carrito: " + e.getMessage());
            throw e; // Vuelve a lanzar la excepción para que el llamador la maneje
        }
        return lista; // Devuelve la lista de detalles del carrito
    }

    /**
     * Actualiza un detalle existente en la tabla.
     * @param detalle el objeto con los datos actualizados.
     * @throws SQLException si ocurre un error.
     */
    public void actualizar(DetalleCarrito detalle) throws SQLException {
        String sql = "UPDATE detalles_carrito SET id_carrito = ?, id_producto = ?, cantidad = ?, subtotal = ? WHERE id_detalle = ?"; // Consulta SQL para actualizar
        try (PreparedStatement stmt = con.prepareStatement(sql)) { // Prepara la consulta
            stmt.setInt(1, detalle.getId_carrito()); // Asigna el ID del carrito
            stmt.setInt(2, detalle.getId_producto()); // Asigna el ID del producto
            stmt.setInt(3, detalle.getCantidad()); // Asigna la cantidad
            stmt.setDouble(4, detalle.getSubtotal()); // Asigna el subtotal
            stmt.setInt(5, detalle.getId_detalle()); // Asigna el ID del detalle a actualizar
            stmt.executeUpdate(); // Ejecuta el UPDATE
        } catch (SQLException e) {
            // Mensaje de error personalizado para problemas de SQL
            System.err.println("Error al actualizar el detalle del carrito: " + e.getMessage());
            throw e; // Vuelve a lanzar la excepción para que el llamador la maneje
        }
    }

    /**
     * Elimina un detalle por su ID.
     * @param id_detalle el ID del detalle a eliminar.
     * @throws SQLException si ocurre un error.
     */
    public void eliminar(int id_detalle) throws SQLException {
        String sql = "DELETE FROM detalles_carrito WHERE id_detalle = ?"; // Consulta SQL para eliminar
        try (PreparedStatement stmt = con.prepareStatement(sql)) { // Prepara la consulta
            stmt.setInt(1, id_detalle); // Asigna el ID del detalle a eliminar
            stmt.executeUpdate(); // Ejecuta el DELETE
        } catch (SQLException e) {
            // Mensaje de error personalizado para problemas de SQL
            System.err.println("Error al eliminar el detalle del carrito: " + e.getMessage());
            throw e; // Vuelve a lanzar la excepción para que el llamador la maneje
        }
    }
}
