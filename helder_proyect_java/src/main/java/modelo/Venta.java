package modelo;

import java.util.Date;

public class Venta {
    private int id_venta;
    private int id_usuario;
    private Date fecha;
    private double total;
    private int id_metodo;
    private String estado;
    private int id_carrito; // Clave foránea hacia carrito

    // Getters y Setters
    public int getId_venta() { return id_venta; }
    public void setId_venta(int id_venta) { this.id_venta = id_venta; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public int getId_metodo() { return id_metodo; }
    public void setId_metodo(int id_metodo) { this.id_metodo = id_metodo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getId_carrito() { return id_carrito; }
    public void setId_carrito(int id_carrito) { this.id_carrito = id_carrito; }
}
