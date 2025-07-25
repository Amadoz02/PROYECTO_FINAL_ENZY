package controlador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.*;
import modelo.Talla;
import modelo.TallaDAO;

@Path("/tallas") // Ruta: http://localhost:8080/helder/api/tallas
public class TallaResource {

    // GET: Lista todas las tallas
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Talla> obtenerTodos() throws Exception {
        TallaDAO dao = new TallaDAO(); // Ya no pasamos la conexión aquí
        return dao.listar(); // Llama al método "listar" del DAO
    }

    // POST: Agrega una nueva talla
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void agregarTalla(Talla talla) throws Exception {
        TallaDAO dao = new TallaDAO();
        dao.insertar(talla);
    }

    // PUT: Actualiza una talla existente
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizarTalla(Talla talla) throws Exception {
        TallaDAO dao = new TallaDAO();
        dao.actualizar(talla);
    }

    // DELETE: Elimina una talla por su ID
    @DELETE
    @Path("/{id}")
    public void eliminarTalla(@PathParam("id") int id_talla) throws Exception {
        TallaDAO dao = new TallaDAO();
        dao.eliminar(id_talla);
    }
}
