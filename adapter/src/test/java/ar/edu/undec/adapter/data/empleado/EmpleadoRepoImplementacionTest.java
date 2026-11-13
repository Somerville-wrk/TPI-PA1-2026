package ar.edu.undec.adapter.data.empleado;

import ar.edu.undec.adapter.data.empleado.crud.EmpleadoCrudRepository;
import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoDataMapper;
import ar.edu.undec.adapter.data.empleado.repoimplementacion.EmpleadoRepoImplementacion;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({EmpleadoRepoImplementacion.class, EmpleadoDataMapper.class})
class EmpleadoRepoImplementacionTest {

    @Autowired
    private EmpleadoRepositoryPort empleadoRepositoryPort;

    @Autowired
    private EmpleadoCrudRepository empleadoCrudRepository;

    @Test
    void guardar_cuandoEmpleadoEsValido_persisteEnBaseDeDatosYRetornaDominio() {
        UUID idGenerado = UUID.randomUUID();
        Empleado nuevoEmpleado = Empleado.instance(
                idGenerado,
                "Camila Fernandez",
                "ING1024",
                LocalDate.now().minusMonths(3),
                Puesto.SUPERVISOR,
                850000.0
        );

        Empleado guardado = empleadoRepositoryPort.guardar(nuevoEmpleado);

        assertNotNull(guardado);
        assertEquals(idGenerado, guardado.getId());
        assertEquals("ING1024", guardado.getLegajo());
        assertTrue(empleadoCrudRepository.findById(idGenerado).isPresent());
    }
}