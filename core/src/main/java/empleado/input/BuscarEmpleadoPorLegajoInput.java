package empleado.input;

import empleado.modelo.Empleado;

public interface BuscarEmpleadoPorLegajoInput {
    Empleado buscarPorLegajo(String legajo);
}