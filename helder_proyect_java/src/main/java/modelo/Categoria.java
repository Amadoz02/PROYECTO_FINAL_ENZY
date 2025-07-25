/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase que representa una categoría de producto en el sistema Urban_Pro.
 */
public class Categoria {
    
    // Atributo que guarda el ID de la categoría
    private int id_categoria;
    
    // Atributo que guarda el nombre de la categoría (ej: "Ropa", "Accesorios")
    private String nombre;
    
    // Atributo que indica el estado de la categoría (ej: "Activo", "Inactivo")
    private String estado;

    /**
     * Constructor vacío. Es necesario para algunos frameworks o herramientas.
     */
    public Categoria() {}

    /**
     * Constructor que inicializa todos los atributos de la clase.
     * 
     * @param id_categoria Identificador único de la categoría
     * @param nombre Nombre de la categoría
     * @param estado Estado de la categoría
     */
    public Categoria(int id_categoria, String nombre, String estado) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
        this.estado = estado;
    }

    // Métodos getters y setters para cada atributo:

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
