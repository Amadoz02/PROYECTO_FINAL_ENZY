package modelo;

/**
 * Clase que representa un Rol dentro del sistema.
 * Un rol puede tener uno o varios permisos y se asigna a un usuario.
 */
public class Rol {
    private int id_rol;         // Identificador único del rol
    private String nombre_rol;  // Nombre del rol (Ej: Administrador, Cliente)

    // Constructor vacío (requerido por JAX-RS)
    public Rol() {}

    // Constructor con parámetros
    public Rol(int id_rol, String nombre_rol) {
        this.id_rol = id_rol;
        this.nombre_rol = nombre_rol;
    }

    // Métodos getter y setter para cada atributo

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }
}
