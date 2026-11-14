package empleado.input;

import empleado.modelo.Puesto;

import java.time.LocalDate;

public record CrearEmpleadoCommand(
        String nombreCompleto,
        String legajo,
        LocalDate fechaIngreso,
        Puesto puesto,
        Double salario
) {
}