
package controlador;

import jakarta.ws.rs.ApplicationPath;
import java.sql.Connection;
import org.glassfish.jersey.server.ResourceConfig;


import static controlador.Conexion.getConexion;

@ApplicationPath("/api") // Esto define el prefijo de la ruta
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        packages("controlador"); // Asegúrate de poner el paquete correcto donde están tus @Path
        try {
            Connection con = getConexion();
            System.out.println(GREEN + " Conexión exitosa a base de datos." + RESET);
            con.close();
        } catch (Exception e) {
            System.out.println(RED + " Error al conectar con la base de datos:" + RESET);
            e.printStackTrace();
        }
    }

    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
}

