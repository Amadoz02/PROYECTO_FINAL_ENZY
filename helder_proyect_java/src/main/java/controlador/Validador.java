/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.function.Supplier;

public class Validador {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    // Validación genérica usando anotaciones Bean Validation (Jakarta EE)
    public static <T> void validar(T objeto) throws CustomException {
        Set<ConstraintViolation<T>> violations = validator.validate(objeto);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new CustomException(sb.toString(), 400); // Código HTTP 400 (Bad Request)
        }
    }

    // Validaciones personalizadas (lógica de negocio)
    public static void validarLongitudCampo(String campo, String nombreCampo, int min, int max) throws CustomException {
        if (campo == null || campo.trim().isEmpty()) {
            throw new CustomException(nombreCampo + " no puede estar vacío", 400);
        }
        if (campo.length() < min || campo.length() > max) {
            throw new CustomException(nombreCampo + " debe tener entre " + min + " y " + max + " caracteres", 400);
        }
    }

    public static void validarEmail(String email) throws CustomException {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+[.A-Za-z]+$")) {
            throw new CustomException("El email no tiene un formato válido", 400);
        }
    }

    // Ejemplo para validar IDs en la base de datos
    public static void validarIdExistente(Integer id, String entidad, Supplier<Boolean> existeEnDb) throws CustomException {
        if (id == null || !existeEnDb.get()) {
            throw new CustomException(entidad + " con ID " + id + " no existe", 404);
        }
    }
}