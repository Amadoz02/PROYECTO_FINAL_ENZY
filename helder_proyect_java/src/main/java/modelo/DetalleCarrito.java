package modelo;

/**
 * Modelo que representa un detalle dentro de un carrito de compras.
 */
public class DetalleCarrito {

    // Atributos correspondientes a las columnas de la tabla
    private int id_detalle;
    private int id_carrito;
    private int id_producto;
    private int cantidad;
    private double subtotal;

    // Constructor vacío necesario para frameworks y deserialización
    public DetalleCarrito() {}

    // Constructor con todos los campos
    public DetalleCarrito(int id_detalle, int id_carrito, int id_producto, int cantidad, double subtotal) {
        this.id_detalle = id_detalle;
        this.id_carrito = id_carrito;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // Getters y Setters

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_carrito() {
        return id_carrito;
    }

    public void setId_carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
