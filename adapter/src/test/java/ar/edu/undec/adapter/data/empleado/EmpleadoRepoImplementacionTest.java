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
import java.util.List;
import java.util.Optional;
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

    @Test
    void existePorLegajo_cuandoLegajoEstaRegistrado_retornaTrueYFalseSiNoExiste() {
        Empleado empleado = Empleado.instance(
                UUID.randomUUID(),
                "Rodolfo Gaston Somerville",
                "EMP1234",
                LocalDate.now().minusYears(1),
                Puesto.GERENTE,
                1500000.0
        );
        empleadoRepositoryPort.guardar(empleado);

        assertTrue(empleadoRepositoryPort.existePorLegajo("EMP1234"));
        assertFalse(empleadoRepositoryPort.existePorLegajo("NOE9999"));
    }

    @Test
    void buscarPorLegajo_cuandoLegajoExiste_retornaOptionalConEmpleadoYVacioSiNoExiste() {
        UUID idEsperado = UUID.randomUUID();
        Empleado empleado = Empleado.instance(
                idEsperado,
                "Ana Lopez",
                "ANA1001",
                LocalDate.now().minusMonths(6),
                Puesto.ANALISTA,
                550000.0
        );
        empleadoRepositoryPort.guardar(empleado);

        Optional<Empleado> encontrado = empleadoRepositoryPort.buscarPorLegajo("ANA1001");
        Optional<Empleado> noEncontrado = empleadoRepositoryPort.buscarPorLegajo("NOE9999");

        assertTrue(encontrado.isPresent());
        assertEquals(idEsperado, encontrado.get().getId());
        assertEquals("Ana Lopez", encontrado.get().getNombreCompleto());
        assertTrue(noEncontrado.isEmpty());
    }

    @Test
    void obtenerTodos_cuandoExistenEmpleadosGuardados_retornaListaCompleta() {
        Empleado emp1 = Empleado.instance(
                UUID.randomUUID(), "Ana Lopez", "ANA1001", LocalDate.now().minusMonths(6), Puesto.ANALISTA, 550000.0);
        Empleado emp2 = Empleado.instance(
                UUID.randomUUID(), "Carlos Ruiz", "CAR2002", LocalDate.now().minusYears(2), Puesto.SUPERVISOR, 900000.0);

        empleadoRepositoryPort.guardar(emp1);
        empleadoRepositoryPort.guardar(emp2);

        List<Empleado> empleados = empleadoRepositoryPort.obtenerTodos();

        assertNotNull(empleados);
        assertEquals(2, empleados.size());
    }
}