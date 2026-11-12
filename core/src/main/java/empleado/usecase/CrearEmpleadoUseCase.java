package empleado.usecase;

import empleado.exception.EmpleadoYaExisteException;
import empleado.input.CrearEmpleadoCommand;
import empleado.input.CrearEmpleadoInput;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepositoryPort;

import java.util.UUID;

public class CrearEmpleadoUseCase implements CrearEmpleadoInput {

    private final EmpleadoRepositoryPort empleadoRepositoryPort;

    public CrearEmpleadoUseCase(EmpleadoRepositoryPort empleadoRepositoryPort) {
        this.empleadoRepositoryPort = empleadoRepositoryPort;
    }

    @Override
    public Empleado crearEmpleado(CrearEmpleadoCommand command) {
        if (empleadoRepositoryPort.existePorLegajo(command.legajo())) {
            throw new EmpleadoYaExisteException("Ya existe un empleado registrado con el legajo " + command.legajo());
        }

        Empleado nuevoEmpleado = Empleado.instance(
                UUID.randomUUID(),
                command.nombreCompleto(),
                command.legajo(),
                command.fechaIngreso(),
                command.puesto(),
                command.salario()
        );

        return empleadoRepositoryPort.guardar(nuevoEmpleado);
    }
}