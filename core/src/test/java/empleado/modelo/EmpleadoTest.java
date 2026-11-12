package empleado.modelo;

import empleado.exception.EmpleadoInvalidoException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.SalarioInvalidoException;
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

    @Test
    void crearEmpleado_conFormatoLegajoIncorrecto_lanzaLegajoInvalidoException() {
        UUID id = UUID.randomUUID();
        LocalDate hoy = LocalDate.now();

        assertThrows(LegajoInvalidoException.class, () ->
                Empleado.instance(id, "Ana Gomez", "abc1234", hoy, Puesto.ANALISTA, 500000.0));
        assertThrows(LegajoInvalidoException.class, () ->
                Empleado.instance(id, "Ana Gomez", "AB1234", hoy, Puesto.ANALISTA, 500000.0));
        assertThrows(LegajoInvalidoException.class, () ->
                Empleado.instance(id, "Ana Gomez", "ABC123", hoy, Puesto.ANALISTA, 500000.0));
        assertThrows(LegajoInvalidoException.class, () ->
                Empleado.instance(id, "Ana Gomez", "ABC12345", hoy, Puesto.ANALISTA, 500000.0));
    }

    @Test
    void crearEmpleado_conFechaIngresoFutura_lanzaFechaIngresoInvalidaException() {
        UUID id = UUID.randomUUID();
        LocalDate maniana = LocalDate.now().plusDays(1);

        assertThrows(FechaIngresoInvalidaException.class, () ->
                Empleado.instance(id, "Carlos Lopez", "XYZ9876", maniana, Puesto.SUPERVISOR, 850000.0));
    }

    @Test
    void crearEmpleado_conSalarioInvalidoOMenorAlPiso_lanzaSalarioInvalidoException() {
        UUID id = UUID.randomUUID();
        LocalDate hoy = LocalDate.now();

        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.instance(id, "Pedro Ruiz", "DEF4567", hoy, Puesto.ANALISTA, 0.0));
        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.instance(id, "Pedro Ruiz", "DEF4567", hoy, Puesto.ANALISTA, -100.0));

        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.instance(id, "Pedro Ruiz", "DEF4567", hoy, Puesto.ANALISTA, 499999.99));
        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.instance(id, "Pedro Ruiz", "DEF4567", hoy, Puesto.SUPERVISOR, 799999.0));
        assertThrows(SalarioInvalidoException.class, () ->
                Empleado.instance(id, "Pedro Ruiz", "DEF4567", hoy, Puesto.GERENTE, 1199999.0));
    }

    @Test
    void crearEmpleado_enLimiteExactoDePisoSalarial_creaInstanciasCorrectamente() {
        UUID id = UUID.randomUUID();
        LocalDate hoy = LocalDate.now();

        assertDoesNotThrow(() ->
                Empleado.instance(id, "Analista Justo", "ANA0001", hoy, Puesto.ANALISTA, 500000.0));
        assertDoesNotThrow(() ->
                Empleado.instance(id, "Supervisor Justo", "SUP0002", hoy, Puesto.SUPERVISOR, 800000.0));
        assertDoesNotThrow(() ->
                Empleado.instance(id, "Gerente Justo", "GER0003", hoy, Puesto.GERENTE, 1200000.0));
    }
}