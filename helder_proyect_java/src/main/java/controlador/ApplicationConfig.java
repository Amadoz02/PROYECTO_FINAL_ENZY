package controlador;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        // Registra tus recursos
        packages("controlador");
        // Configura CORS
        register(CorsFilter.class);
    }
}

