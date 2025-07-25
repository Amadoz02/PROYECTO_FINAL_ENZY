package modelo;

/**
 * Clase que representa una dirección asociada a un usuario.
 * Contiene información como la ciudad, departamento, código postal, etc.
 */
public class Direccion {

    // Identificador único de la dirección
    private int id_direccion;

    // ID del usuario al que pertenece la dirección
    private int id_usuario;

    // Texto de la dirección (nombre de calle, número, etc.)
    private String direccion;

    // Ciudad de la dirección
    private String ciudad;

    // Departamento (provincia o estado) de la dirección
    private String departamento;

    // Código postal asociado a la dirección
    private String codigo_postal;

    // Observaciones adicionales sobre la dirección
    private String observaciones;

    // Métodos getter y setter para acceder y modificar los atributos

    public int getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(int id_direccion) {
        this.id_direccion = id_direccion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
