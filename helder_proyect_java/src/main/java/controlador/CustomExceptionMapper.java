/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import modelo.ApiResponse;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        ApiResponse response = new ApiResponse();
        response.setStatus("error");
        response.setMessage(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(response)
                       .build();
    }
}

