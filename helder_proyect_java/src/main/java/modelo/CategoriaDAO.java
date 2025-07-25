package modelo;

import controlador.Conexion; // Importa la clase de conexión a la base de datos
import java.sql.*; // Importa las clases necesarias para trabajar con SQL
import java.util.ArrayList; // Importa la clase ArrayList para manejar listas
import java.util.List; // Importa la interfaz List
import java.util.logging.Level; // Importa la clase Level para el registro de logs
import java.util.logging.Logger; // Importa la clase Logger para el registro de logs

/**
 * Clase encargada de gestionar todas las operaciones CRUD con la tabla 'categorias'.
 */
public class CategoriaDAO {

    // Método para insertar una nueva categoría en la base de datos
    public boolean insertar(Categoria categoria) throws Exception {
        boolean insertado = false; // Variable para verificar si se insertó correctamente
        try (Connection conn = Conexion.getConexion()) { // Establece la conexión a la base de datos
            String sql = "INSERT INTO categorias (nombre, estado) VALUES (?, ?)"; // Consulta SQL para insertar
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara la consulta
            stmt.setString(1, categoria.getNombre()); // Se asigna el nombre recibido
            if (categoria.getEstado()=="Inactivo" || categoria.getEstado()=="Activo"){
                 stmt.setString(2, categoria.getEstado());  // Se asigna el estado recibido
               
            }else{
                 stmt.setString(2, "Activo");  // Se asigna el estado recibido
            }
           

            int filas = stmt.executeUpdate(); // Ejecuta el INSERT
            insertado = filas > 0;            // Verifica si se insertó al menos 1 fila
        } catch (SQLException e) {
            // Mensaje de error personalizado para problemas de SQL
            System.err.println("Error al insertar la categoría: " + e.getMessage());
        }
        return insertado; // Devuelve el resultado de la inserción
    }

    // Método para listar todas las categorías de la base de datos
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>(); // Lista para almacenar las categorías
        try (Connection conn = Conexion.getConexion()) { // Establece la conexión a la base de datos
            String sql = "SELECT * FROM categorias"; // Consulta SQL para seleccionar todas las categorías
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara la consulta
            ResultSet rs = stmt.executeQuery();  // Ejecuta el SELECT

            // Itera sobre los resultados y crea objetos Categoria
            while (rs.next()) {
                Categoria c = new Categoria(); // Crea una nueva instancia de Categoria
                c.setId_categoria(rs.getInt("id_categoria")); // ID de la categoría
                c.setNombre(rs.getString("nombre"));          // Nombre de la categoría
                c.setEstado(rs.getString("estado"));          // Estado actual
                lista.add(c);                                 // Se agrega a la lista final
            }
        } catch (SQLException e) {
            // Mensaje de error personalizado para problemas de SQL
            System.err.println("Error al listar las categorías: " + e.getMessage());
        } catch (Exception ex) {
            // Mensaje de error para cualquier otra excepción
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, "Error inesperado al listar categorías", ex);
        }
        return lista; // Devuelve la lista de categorías
    }

    // Método para actualizar una categoría existente
    public boolean actualizar(Categoria categoria) {
        boolean actualizado = false; // Variable para verificar si se actualizó correctamente
        try (Connection conn = Conexion.getConexion()) { // Establece la conexión a la base de datos
            String sql = "UPDATE categorias SET nombre = ?, estado = ? WHERE id_categoria = ?"; // Consulta SQL para actualizar
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara la consulta
            stmt.setString(1, categoria.getNombre()); // Asigna el nuevo nombre
            if (categoria.getEstado()=="Inactivo" || categoria.getEstado()=="Activo"){
                 stmt.setString(2, categoria.getEstado());  // Se asigna el estado recibido
               
            }else{
                 stmt.setString(2, "Activo");  // Se asigna el estado recibido
            } // Asigna el nuevo estado
            stmt.setInt(3, categoria.getId_categoria()); // Asigna el ID de la categoría a actualizar

            int filas = stmt.executeUpdate(); // Ejecuta el UPDATE
            actualizado = filas > 0; // Se actualizó si hay al menos una fila afectada
        } catch (SQLException e) {
            // Mensaje de error personalizado para problemas de SQL
            System.err.println("Error al actualizar la categoría: " + e.getMessage());
        } catch (Exception ex) {
            // Mensaje de error para cualquier otra excepción
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, "Error inesperado al actualizar la categoría", ex);
        }
        return actualizado; // Devuelve el resultado de la actualización
    }

    // Método para eliminar una categoría por su ID
    public boolean eliminar(int id_categoria) {
        boolean eliminado = false; // Variable para verificar si se eliminó correctamente
        try (Connection conn = Conexion.getConexion()) { // Establece la conexión a la base de datos
            String sql = "DELETE FROM categorias WHERE id_categoria = ?"; // Consulta SQL para eliminar
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepara la consulta
            stmt.setInt(1, id_categoria); // Asigna el ID de la categoría a eliminar

            int filas = stmt.executeUpdate(); // Ejecuta el DELETE
            eliminado = filas > 0; // Se eliminó si hay al menos una fila afectada
        } catch (SQLException e) {
            // Mensaje de error personalizado para problemas de SQL
            System.err.println("Error al eliminar la categoría: " + e.getMessage());
        } catch (Exception ex) {
            // Mensaje de error para cualquier otra excepción
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, "Error inesperado al eliminar la categoría", ex);
        }
        return eliminado; // Devuelve el resultado de la eliminación
    }
}

