package empleado.output;

import empleado.modelo.Empleado;


public interface EmpleadoRepositoryPort {
    boolean existePorLegajo(String legajo);
    Empleado guardar(Empleado empleado);

}