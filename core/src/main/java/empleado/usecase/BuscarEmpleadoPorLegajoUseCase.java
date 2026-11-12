package empleado.usecase;

import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepositoryPort;

public class BuscarEmpleadoPorLegajoUseCase implements BuscarEmpleadoPorLegajoInput {

    private final EmpleadoRepositoryPort empleadoRepositoryPort;

    public BuscarEmpleadoPorLegajoUseCase(EmpleadoRepositoryPort empleadoRepositoryPort) {
        this.empleadoRepositoryPort = empleadoRepositoryPort;
    }

    @Override
    public Empleado buscarPorLegajo(String legajo) {
        return null;
    }
}