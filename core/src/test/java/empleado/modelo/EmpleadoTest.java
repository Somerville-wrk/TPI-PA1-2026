package empleado.modelo;

import empleado.exception.EmpleadoInvalidoException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoTest {

    @Test
    void crearEmpleado_conDatosValidos_retornaInstanciaCorrecta() {
        UUID id = UUID.randomUUID();
        LocalDate fechaIngreso = LocalDate.now().minusDays(10);

        Empleado empleado = Empleado.instance(
                id,
                "Rodolfo Gaston Somerville",
                "EMP1234",
                fechaIngreso,
                Puesto.ANALISTA,
                550000.0
        );

        assertNotNull(empleado);
        assertEquals(id, empleado.getId());
        assertEquals("Rodolfo Gaston Somerville", empleado.getNombreCompleto());
        assertEquals("EMP1234", empleado.getLegajo());
        assertEquals(fechaIngreso, empleado.getFechaIngreso());
        assertEquals(Puesto.ANALISTA, empleado.getPuesto());
        assertEquals(550000.0, empleado.getSalario());
    }

    @Test
    void crearEmpleado_conCamposObligatoriosNulosOVacios_lanzaEmpleadoInvalidoException() {
        UUID id = UUID.randomUUID();
        LocalDate hoy = LocalDate.now();

        assertThrows(EmpleadoInvalidoException.class, () ->
                Empleado.instance(null, "Juan Perez", "ABC1234", hoy, Puesto.ANALISTA, 500000.0));

        assertThrows(EmpleadoInvalidoException.class, () ->
                Empleado.instance(id, null, "ABC1234", hoy, Puesto.ANALISTA, 500000.0));
        assertThrows(EmpleadoInvalidoException.class, () ->
                Empleado.instance(id, "   ", "ABC1234", hoy, Puesto.ANALISTA, 500000.0));

        assertThrows(EmpleadoInvalidoException.class, () ->
                Empleado.instance(id, "Juan Perez", null, hoy, Puesto.ANALISTA, 500000.0));
        assertThrows(EmpleadoInvalidoException.class, () ->
                Empleado.instance(id, "Juan Perez", "", hoy, Puesto.ANALISTA, 500000.0));

        assertThrows(EmpleadoInvalidoException.class, () ->
                Empleado.instance(id, "Juan Perez", "ABC1234", null, Puesto.ANALISTA, 500000.0));
        assertThrows(EmpleadoInvalidoException.class, () ->
                Empleado.instance(id, "Juan Perez", "ABC1234", hoy, null, 500000.0));
        assertThrows(EmpleadoInvalidoException.class, () ->
                Empleado.instance(id, "Juan Perez", "ABC1234", hoy, Puesto.ANALISTA, null));
    }
}