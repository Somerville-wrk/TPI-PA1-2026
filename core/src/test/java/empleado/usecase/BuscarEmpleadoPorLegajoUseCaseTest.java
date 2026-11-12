package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarEmpleadoPorLegajoUseCaseTest {

    @Mock
    private EmpleadoRepositoryPort empleadoRepositoryPort;

    @InjectMocks
    private BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase;

    @Test
    void buscarPorLegajo_cuandoEmpleadoExiste_retornaEmpleado() {
        Empleado empleadoExistente = Empleado.instance(
                UUID.randomUUID(),
                "Rodolfo Gaston Somerville",
                "EMP1234",
                LocalDate.now().minusYears(1),
                Puesto.GERENTE,
                1500000.0
        );

        when(empleadoRepositoryPort.buscarPorLegajo("EMP1234"))
                .thenReturn(Optional.of(empleadoExistente));

        Empleado resultado = buscarEmpleadoPorLegajoUseCase.buscarPorLegajo("EMP1234");

        assertNotNull(resultado);
        assertEquals(empleadoExistente.getId(), resultado.getId());
        assertEquals("EMP1234", resultado.getLegajo());
        assertEquals("Rodolfo Gaston Somerville", resultado.getNombreCompleto());
        verify(empleadoRepositoryPort).buscarPorLegajo("EMP1234");
    }

    @Test
    void buscarPorLegajo_cuandoEmpleadoNoExiste_lanzaEmpleadoNoEncontradoException() {
        when(empleadoRepositoryPort.buscarPorLegajo("NOE9999"))
                .thenReturn(Optional.empty());

        assertThrows(EmpleadoNoEncontradoException.class, () ->
                buscarEmpleadoPorLegajoUseCase.buscarPorLegajo("NOE9999"));

        verify(empleadoRepositoryPort).buscarPorLegajo("NOE9999");
    }
}