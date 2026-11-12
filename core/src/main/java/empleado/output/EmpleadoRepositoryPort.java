package empleado.output;

import empleado.modelo.Empleado;

import java.util.Optional;
import java.util.List;

public interface EmpleadoRepositoryPort {
    boolean existePorLegajo(String legajo);
    Empleado guardar(Empleado empleado);
    Optional<Empleado> buscarPorLegajo(String legajo);
    List<Empleado> obtenerTodos();
}
