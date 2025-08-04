package modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase para encapsular respuestas de la API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Incluye solo campos no nulos
public class ApiResponse {
    
    @JsonProperty("status") // Anotación para serializar el campo
    private String status;
    
    @JsonProperty("message") // Anotación para serializar el campo
    private String message;
    
    @JsonProperty("data") // Anotación para serializar el campo
    private Object data;

    // Getters y Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; } // Cambiado a Object
}
