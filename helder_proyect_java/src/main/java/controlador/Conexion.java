
// Indica que esta clase pertenece al paquete llamado "controlador"
package controlador;

// Importa las clases necesarias para conectarse y manejar errores
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


/**
 * Clase Conexion
 * ----------------
 * Esta clase se encarga de establecer una conexión con la base de datos MySQL.
 * Proporciona un único método llamado "getConexion" que devuelve una conexión lista para usar.
 */
public class Conexion {

    /**
     * Método getConexion
     * -------------------
     * Este método intenta conectarse a la base de datos especificada
     * usando los datos definidos (URL, usuario y contraseña).
     *
     * @return una conexión válida a la base de datos
     * @throws Exception si ocurre algún problema durante la conexión
     */
    public static Connection getConexion() throws Exception {

        // Dirección completa de la base de datos (host, puerto, nombre de la base)
        String URL = "jdbc:mysql://localhost:3306/urban_pro";

        // Nombre de usuario para conectarse a la base de datos
        String USER = "root";

        // Contraseña del usuario "root"
        String PASSWORD = "#Aprendiz2024";

        // Carga el controlador de MySQL necesario para conectar Java con MySQL
        // Esta línea registra el driver para que pueda ser usado por el DriverManager
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Intenta establecer y devolver una conexión usando los datos anteriores
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
