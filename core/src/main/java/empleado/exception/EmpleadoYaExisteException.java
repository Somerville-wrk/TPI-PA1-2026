package empleado.exception;

public class EmpleadoYaExisteException extends RuntimeException {
    public EmpleadoYaExisteException(String message) {
        super(message);
    }
}
