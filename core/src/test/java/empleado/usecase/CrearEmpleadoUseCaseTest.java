package empleado.usecase;

import empleado.exception.EmpleadoYaExisteException;
import empleado.input.CrearEmpleadoCommand;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearEmpleadoUseCaseTest {

    @Mock
    private EmpleadoRepositoryPort empleadoRepositoryPort;

    @InjectMocks
    private CrearEmpleadoUseCase crearEmpleadoUseCase;

    @Captor
    private ArgumentCaptor<Empleado> empleadoCaptor;

    @Test
    void crearEmpleado_conDatosValidosYLegajoDisponible_guardaYRetornaEmpleado() {
        CrearEmpleadoCommand command = new CrearEmpleadoCommand(
                "Camila Fernandez",
                "ING1024",
                LocalDate.now().minusMonths(2),
                Puesto.SUPERVISOR,
                900000.0
        );

        when(empleadoRepositoryPort.existePorLegajo("ING1024")).thenReturn(false);
        when(empleadoRepositoryPort.guardar(any(Empleado.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Empleado resultado = crearEmpleadoUseCase.crearEmpleado(command);

        verify(empleadoRepositoryPort).existePorLegajo("ING1024");
        verify(empleadoRepositoryPort).guardar(empleadoCaptor.capture());

        Empleado empleadoCapturado = empleadoCaptor.getValue();
        assertNotNull(empleadoCapturado.getId());
        assertEquals("Camila Fernandez", empleadoCapturado.getNombreCompleto());
        assertEquals("ING1024", empleadoCapturado.getLegajo());
        assertEquals(Puesto.SUPERVISOR, empleadoCapturado.getPuesto());
        assertEquals(900000.0, empleadoCapturado.getSalario());

        assertNotNull(resultado);
        assertEquals(empleadoCapturado.getId(), resultado.getId());
    }

    @Test
    void crearEmpleado_cuandoLegajoYaExiste_lanzaEmpleadoYaExisteExceptionYNoGuarda() {
        CrearEmpleadoCommand command = new CrearEmpleadoCommand(
                "Juan Duplicado",
                "DUP1111",
                LocalDate.now().minusDays(5),
                Puesto.ANALISTA,
                550000.0
        );

        when(empleadoRepositoryPort.existePorLegajo("DUP1111")).thenReturn(true);

        assertThrows(EmpleadoYaExisteException.class, () ->
                crearEmpleadoUseCase.crearEmpleado(command));

        verify(empleadoRepositoryPort).existePorLegajo("DUP1111");
        verify(empleadoRepositoryPort, never()).guardar(any(Empleado.class));
    }
}