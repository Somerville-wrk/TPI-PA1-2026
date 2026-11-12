package empleado.usecase;

import empleado.input.ListarEmpleadosInput;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepositoryPort;

import java.util.List;

public class ListarEmpleadosUseCase implements ListarEmpleadosInput {

    private final EmpleadoRepositoryPort empleadoRepositoryPort;

    public ListarEmpleadosUseCase(EmpleadoRepositoryPort empleadoRepositoryPort) {
        this.empleadoRepositoryPort = empleadoRepositoryPort;
    }

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepositoryPort.obtenerTodos();
    }
}