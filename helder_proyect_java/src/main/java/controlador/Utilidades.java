package controlador; // Esta línea indica que esta clase pertenece al paquete llamado "utils" (utilidades)

/**
 * Clase Utilidades
 * ----------------
 * Esta clase ofrece métodos útiles y reutilizables para el sistema.
 * En este caso, contiene un método para encriptar contraseñas utilizando el algoritmo SHA-256.
 */
import java.security.MessageDigest; // Librería que permite trabajar con funciones de encriptación como SHA-256

public class Utilidades {

    /**
     * Método encriptarSHA256
     * -----------------------
     * Este método recibe un texto (por ejemplo una contraseña) y devuelve su versión encriptada con SHA-256.
     * Esto se hace para no guardar contraseñas en texto plano y mejorar la seguridad del sistema.
     *
     * @param texto El texto que se desea encriptar (normalmente una contraseña)
     * @return El texto convertido en una cadena hexadecimal encriptada (irreversible)
     */
    public static String encriptarSHA256(String texto) {
        try {
            // Crea una instancia del algoritmo de encriptación SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Convierte el texto original a un arreglo de bytes en formato UTF-8 y lo encripta
            byte[] hash = md.digest(texto.getBytes("UTF-8"));

            // Se usará para construir la cadena en formato hexadecimal
            StringBuilder hex = new StringBuilder();

            // Este bucle recorre cada byte del resultado encriptado
            for (byte b : hash) {
                // Convierte cada byte a formato hexadecimal (dos caracteres) y lo agrega al resultado
                hex.append(String.format("%02x", b));
            }

            // Devuelve la cadena encriptada como texto plano hexadecimal
            return hex.toString();

        } catch (Exception e) {
            // Si ocurre un error, se lanza una excepción de tipo Runtime para que se detenga el programa
            throw new RuntimeException(e);
        }
    }
}
