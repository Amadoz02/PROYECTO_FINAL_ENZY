package modelo;

import controlador.Conexion;
import java.sql.*;
import java.util.*;

public class TallaDAO {

    // Insertar una nueva talla
    public void insertar(Talla talla) throws Exception {
        String sql = "INSERT INTO tallas (talla) VALUES (?)";

        try (
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setString(1, talla.getTalla()); // Inserta el valor real
            ps.executeUpdate();
        }
    }

    // Obtener todas las tallas
    public List<Talla> listar() throws Exception {
        List<Talla> lista = new ArrayList<>();
        String sql = "SELECT * FROM tallas";

        try (
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                Talla t = new Talla();
                t.setId_talla(rs.getInt("id_talla"));
                t.setTalla(rs.getString("talla")); // <- Recupera correctamente la talla
                lista.add(t);
            }
        }

        return lista;
    }

    // Actualizar una talla existente
    public void actualizar(Talla talla) throws Exception {
        String sql = "UPDATE tallas SET talla = ? WHERE id_talla = ?";

        try (
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setString(1, talla.getTalla());
            ps.setInt(2, talla.getId_talla());
            ps.executeUpdate();
        }
    }

    // Eliminar una talla por ID
    public void eliminar(int id_talla) throws Exception {
        String sql = "DELETE FROM tallas WHERE id_talla = ?";

        try (
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setInt(1, id_talla);
            ps.executeUpdate();
        }
    }
}
