package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
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
        return empleadoRepositoryPort.buscarPorLegajo(legajo)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("No se encontro un empleado con el legajo " + legajo));
    }
}

