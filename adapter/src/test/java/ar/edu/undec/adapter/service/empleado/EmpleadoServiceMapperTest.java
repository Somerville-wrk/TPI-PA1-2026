package ar.edu.undec.adapter.service.empleado;

import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoServiceMapper;
import ar.edu.undec.adapter.service.empleado.model.CrearEmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.input.CrearEmpleadoCommand;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoServiceMapperTest {

    private final EmpleadoServiceMapper mapper = new EmpleadoServiceMapper();

    @Test
    void toCommandYToResponse_mapeaCorrectamenteEntreModelosWebYCore() {
        LocalDate fechaIngreso = LocalDate.now().minusMonths(2);
        CrearEmpleadoRequest request = new CrearEmpleadoRequest(
                "Camila Fernandez",
                "ING1024",
                fechaIngreso,
                Puesto.SUPERVISOR,
                850000.0
        );

        CrearEmpleadoCommand command = mapper.toCommand(request);

        assertNotNull(command);
        assertEquals("Camila Fernandez", command.nombreCompleto());
        assertEquals("ING1024", command.legajo());
        assertEquals(fechaIngreso, command.fechaIngreso());
        assertEquals(Puesto.SUPERVISOR, command.puesto());
        assertEquals(850000.0, command.salario());

        UUID idGenerado = UUID.randomUUID();
        Empleado empleado = Empleado.instance(
                idGenerado,
                "Camila Fernandez",
                "ING1024",
                fechaIngreso,
                Puesto.SUPERVISOR,
                850000.0
        );

        EmpleadoResponse response = mapper.toResponse(empleado);

        assertNotNull(response);
        assertEquals(idGenerado, response.id());
        assertEquals("Camila Fernandez", response.nombreCompleto());
        assertEquals("ING1024", response.legajo());
        assertEquals(fechaIngreso, response.fechaIngreso());
        assertEquals(Puesto.SUPERVISOR, response.puesto());
        assertEquals(850000.0, response.salario());
    }
}