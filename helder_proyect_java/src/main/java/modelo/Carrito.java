package modelo;

/**
 * Clase Carrito
 * Representa un carrito de compras con usuario y venta asociada.
 */
public class Carrito {
    private int id_carrito;
    private int id_cliente;
  
    private int estado;


    // Getters y Setters
    public int getId_carrito() {
        return id_carrito;
    }
    public int getEstado() {
        return estado;
    }

    public void setId_carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    public int getId_usuario() {
        return id_cliente;
    }

    public void setId_usuario(int id_cliente) {
        this.id_cliente = id_cliente;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
}