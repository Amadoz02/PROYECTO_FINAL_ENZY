package controlador;

import modelo.Carrito;
import modelo.CarritoDAO;
import java.sql.Connection;
import java.sql.SQLException;

public class CarritoService {
    private CarritoDAO carritoDAO;

    public CarritoService(Connection con) {
        this.carritoDAO = new CarritoDAO(con);
    }

    /**
     * Obtiene o crea un carrito activo para el usuario
     */
    public Carrito obtenerCarritoActivo(int idUsuario) throws SQLException {
        // 1. Buscar carrito activo existente
        Carrito carrito = carritoDAO.buscarCarritoActivoPorUsuario(idUsuario);
        System.out.println("Carrito obj: " + carrito);

        // 2. Si no existe, crear uno nuevo
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setId_usuario(idUsuario);
            carrito.setEstado("activo"); //activo
            System.out.println("VAMOS A CREAR UN CARRITO YA QUE EL USUARIO NO TIENE UNO ACTIVO ");

            carritoDAO.insertar(carrito);
        }
        
        return carrito;
    }
}
