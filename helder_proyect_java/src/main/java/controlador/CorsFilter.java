package controlador;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

/**
 * Filtro que habilita CORS para permitir peticiones desde el frontend (por ejemplo desde http://localhost:5173).
 */


@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        
        // Permitir origen específico (tu frontend)
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:5173");
        
        // Métodos permitidos
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        
        // Headers permitidos
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        
        // Permitir credenciales si las usas (opcional)
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }
}

