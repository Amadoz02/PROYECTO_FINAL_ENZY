package modelo; // Esta línea indica que esta clase pertenece al paquete llamado "modelo"

import static controlador.Utilidades.encriptarSHA256;


/**
 * Esta clase representa un "Usuario" del sistema.
 * Aquí se definen los datos básicos que identifican a un usuario y se permiten obtenerlos o modificarlos.
 * Esta clase actúa como una "plantilla" para crear objetos con información de un usuario.
 */
public class Usuario {

    // Atributo que almacena el ID único del usuario (clave primaria en base de datos)
    private int id_usuario;

    // Atributo que guarda el nombre completo del usuario
    private String nombre;

    // Atributo que almacena el correo electrónico del usuario
    private String correo;

    // Atributo que guarda la contraseña del usuario (normalmente debe ir encriptada)
    private String contrasena;

    // Atributo que indica el rol del usuario (por ejemplo: administrador, cliente, etc.)
    private int id_rol;

    // Atributo que indica si el usuario está activo, inactivo, bloqueado, etc.
    private String estado;

    // --------------------- MÉTODOS GETTER Y SETTER ---------------------

    /**
     * Método que permite obtener el ID del usuario
     * @return valor numérico del ID
     */
    public int getId_usuario() {
        return id_usuario; // Devuelve el ID del usuario
    }

    /**
     * Método que permite establecer el ID del usuario
     * @param id_usuario valor que se asignará como ID
     */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario; // Asigna el ID recibido al atributo interno
    }

    /**
     * Método que permite obtener el nombre del usuario
     * @return el nombre guardado
     */
    public String getNombre() {
        return nombre; // Devuelve el nombre del usuario
    }

    /**
     * Método que permite asignar el nombre del usuario
     * @param nombre texto que será el nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; // Asigna el nombre recibido al atributo interno
    }

    /**
     * Método que permite obtener el correo electrónico del usuario
     * @return el correo guardado
     */
    public String getCorreo() {
        return correo; // Devuelve el correo del usuario
    }

    /**
     * Método que permite asignar el correo electrónico del usuario
     * @param correo texto del correo que se desea guardar
     */
    public void setCorreo(String correo) {
        this.correo = correo; // Asigna el correo recibido al atributo interno
    }

    /**
     * Método que permite obtener la contraseña del usuario
     * @return la contraseña (aunque por seguridad no siempre se debe mostrar)
     */
    public String getContrasena() {
        return contrasena; // Devuelve la contraseña del usuario
    }

    /**
     * Método que permite establecer la contraseña del usuario
     * @param contrasena texto de la contraseña que se desea guardar
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena; // encriptarSHA256(contrasena)Asigna la contraseña recibida al atributo interno
    }

    /**
     * Método que permite obtener el ID del rol del usuario
     * @return el ID del rol
     */
    public int getId_rol() {
        return id_rol; // Devuelve el ID del rol del usuario
    }

    /**
     * Método que permite establecer el ID del rol del usuario
     * @param id_rol valor numérico del rol que se desea asignar
     */
    public void setId_rol(int id_rol) {
        this.id_rol = id_rol; // Asigna el ID del rol recibido al atributo interno
    }

    /**
     * Método que permite obtener el estado del usuario (ej: activo, inactivo)
     * @return el estado actual del usuario
     */
    public String getEstado() {
        return estado; // Devuelve el estado del usuario
    }

    /**
     * Método que permite establecer el estado del usuario
     * @param estado texto que indica el nuevo estado
     */
    public void setEstado(String estado) {
        this.estado = estado; // Asigna el estado recibido al atributo interno
    }
}
