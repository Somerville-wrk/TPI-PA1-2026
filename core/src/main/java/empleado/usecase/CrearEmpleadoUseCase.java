package empleado.usecase;

import empleado.input.CrearEmpleadoCommand;
import empleado.input.CrearEmpleadoInput;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepositoryPort;

public class CrearEmpleadoUseCase implements CrearEmpleadoInput {

    private final EmpleadoRepositoryPort empleadoRepositoryPort;

    public CrearEmpleadoUseCase(EmpleadoRepositoryPort empleadoRepositoryPort) {
        this.empleadoRepositoryPort = empleadoRepositoryPort;
    }

    @Override
    public Empleado crearEmpleado(CrearEmpleadoCommand command) {
        return null;
    }
}