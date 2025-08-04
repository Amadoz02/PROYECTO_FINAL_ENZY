/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import modelo.DetalleCarrito;
import modelo.DetalleCarritoDAO;
import modelo.Producto;
import modelo.ProductoDAO;

public class DetallesCarritoService {

    private ProductoDAO productoDAO;

    // Constructor que inicializa el DAO de productos
    public DetallesCarritoService() {
        this.productoDAO = new ProductoDAO();
    }

    /**
     * Calcula el subtotal multiplicando precio del producto * cantidad.
     */
    public double calcularSubtotal(int idProducto, int cantidad) {
        try {
            Producto producto = productoDAO.obtenerPorId(idProducto);
            if (producto != null) {
                return producto.getPrecio() * cantidad;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    
    public void agregarOActualizarDetalle(DetalleCarrito detalle) throws SQLException, Exception {
        DetalleCarrito existente = buscarDetallePorCarritoYProducto(detalle.getId_carrito(), detalle.getId_producto());
            System.out.println("----el carritp existe?___"+existente);
        if (existente != null) {
            System.out.println("----esi, existe y eduitamos la cantidad___");

            int nuevaCantidad = existente.getCantidad() + detalle.getCantidad();
            double nuevoSubtotal = calcularSubtotal(detalle.getId_producto(), nuevaCantidad);
            actualizarCantidadYSubtotal(existente.getId_detalle(), nuevaCantidad, nuevoSubtotal);
        } else {
            System.out.println("----no existe lo agregamos___");
            double subtotal = calcularSubtotal(detalle.getId_producto(), detalle.getCantidad());
            detalle.setSubtotal(subtotal);
            Connection con = Conexion.getConexion();
            DetalleCarritoDAO dao = new DetalleCarritoDAO(con);
            dao.insertar(detalle);
        }
    }

    
    /**
     * Verifica si ya existe un detalle con ese producto en ese carrito.
     * Si existe, lo retorna; si no, retorna null.
     */
    public DetalleCarrito buscarDetallePorCarritoYProducto(int idCarrito, int idProducto) throws SQLException, Exception {
        String sql = "SELECT * FROM detalles_carrito WHERE id_carrito = ? AND id_producto = ?";
        
        // Se obtiene una conexión desde la clase Conexion
        try (Connection con = Conexion.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCarrito);
            stmt.setInt(2, idProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                DetalleCarrito detalle = new DetalleCarrito();
                detalle.setId_detalle(rs.getInt("id_detalle"));
                detalle.setId_carrito(rs.getInt("id_carrito"));
                detalle.setId_producto(rs.getInt("id_producto"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setSubtotal(rs.getDouble("subtotal"));
                return detalle;
            }

            return null;
        }
    }

    /**
     * Actualiza cantidad y subtotal de un detalle existente.
     */
    public void actualizarCantidadYSubtotal(int idDetalle, int nuevaCantidad, double nuevoSubtotal) throws SQLException, Exception {
        String sql = "UPDATE detalles_carrito SET cantidad = ?, subtotal = ? WHERE id_detalle = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, nuevaCantidad);
            stmt.setDouble(2, nuevoSubtotal);
            stmt.setInt(3, idDetalle);
            stmt.executeUpdate();
        }
    }
}
