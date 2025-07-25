package modelo;

/**
 * Clase que representa la entidad Talla.
 */
public class Talla {
    private int id_talla;
    private String talla;

    public Talla() {}

    public Talla(int id_talla, String talla) {
        this.id_talla = id_talla;
        this.talla = talla;
    }

    public int getId_talla() {
        return id_talla;
    }

    public void setId_talla(int id_talla) {
        this.id_talla = id_talla;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }
}
