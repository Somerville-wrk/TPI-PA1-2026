package empleado.usecase;

import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarEmpleadosUseCaseTest {

    @Mock
    private EmpleadoRepositoryPort empleadoRepositoryPort;

    @InjectMocks
    private ListarEmpleadosUseCase listarEmpleadosUseCase;

    @Test
    void listarEmpleados_cuandoExistenRegistros_retornaColeccionCompleta() {
        Empleado emp1 = Empleado.instance(
                UUID.randomUUID(), "Ana Lopez", "ANA1001", LocalDate.now().minusMonths(6), Puesto.ANALISTA, 550000.0);
        Empleado emp2 = Empleado.instance(
                UUID.randomUUID(), "Carlos Ruiz", "CAR2002", LocalDate.now().minusYears(2), Puesto.SUPERVISOR, 900000.0);

        when(empleadoRepositoryPort.obtenerTodos()).thenReturn(List.of(emp1, emp2));

        List<Empleado> resultado = listarEmpleadosUseCase.listarEmpleados();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("ANA1001", resultado.get(0).getLegajo());
        assertEquals("CAR2002", resultado.get(1).getLegajo());
        verify(empleadoRepositoryPort).obtenerTodos();
    }
}