package ar.edu.undec.adapter.handler;

import empleado.exception.EmpleadoInvalidoException;
import empleado.exception.EmpleadoYaExisteException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.SalarioInvalidoException;
import empleado.exception.EmpleadoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String mensaje) {
    }

    @ExceptionHandler({
            EmpleadoInvalidoException.class,
            LegajoInvalidoException.class,
            FechaIngresoInvalidaException.class,
            SalarioInvalidoException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmpleadoInvalido(RuntimeException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(EmpleadoYaExisteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmpleadoYaExiste(EmpleadoYaExisteException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmpleadoNoEncontrado(EmpleadoNoEncontradoException ex) {
        return new ErrorResponse(ex.getMessage());
    }

}