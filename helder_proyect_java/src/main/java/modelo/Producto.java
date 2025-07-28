package modelo;

import java.util.List;

public class Producto {
    private int id_producto;
    private String nombre;
    private double precio;
    private String descripcion;
    private int id_talla;
    private int id_categoria;
    private int id_genero;
    private String estado;
    private String talla;
    private String categoria;
    private String genero;
    private List<Imagen> imagenes;

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
}

    // Getters y Setters
    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }


    // Getters y setters
    public int getId_producto() { return id_producto; }
    public void setId_producto(int id_producto) { this.id_producto = id_producto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getId_talla() { return id_talla; }
    public void setId_talla(int id_talla) { this.id_talla = id_talla; }

    public int getId_categoria() { return id_categoria; }
    public void setId_categoria(int id_categoria) { this.id_categoria = id_categoria; }

    public int getId_genero() { return id_genero; }
    public void setId_genero(int id_genero) { this.id_genero = id_genero; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

