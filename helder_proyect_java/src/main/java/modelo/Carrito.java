package modelo;

/**
 * Clase Carrito
 * Representa un carrito de compras con usuario y venta asociada.
 */
public class Carrito {
    public int id_carrito;
    public int id_cliente;
  
    private String estado;


    // Getters y Setters
    public int getId_carrito() {
        return id_carrito;
    }
    public String getEstado() {
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
    public void setEstado(String estado) {
        this.estado = estado;
    }
}