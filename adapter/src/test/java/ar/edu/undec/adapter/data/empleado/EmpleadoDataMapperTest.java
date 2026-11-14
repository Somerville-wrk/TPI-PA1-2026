package ar.edu.undec.adapter.data.empleado;

import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoDataMapper;
import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoDataMapperTest {

    private final EmpleadoDataMapper mapper = new EmpleadoDataMapper();

    @Test
    void toEntityYToDomain_mapeaCorrectamenteTodosLosAtributosPreservandoUuid() {
        UUID id = UUID.randomUUID();
        LocalDate fechaIngreso = LocalDate.now().minusMonths(4);
        Empleado dominioOriginal = Empleado.instance(
                id, "Camila Fernandez", "ING1024", fechaIngreso, Puesto.SUPERVISOR, 850000.0
        );

        EmpleadoEntity entity = mapper.toEntity(dominioOriginal);

        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals("Camila Fernandez", entity.getNombreCompleto());
        assertEquals("ING1024", entity.getLegajo());
        assertEquals(fechaIngreso, entity.getFechaIngreso());
        assertEquals(Puesto.SUPERVISOR, entity.getPuesto());
        assertEquals(850000.0, entity.getSalario());

        Empleado dominioReconstruido = mapper.toDomain(entity);

        assertNotNull(dominioReconstruido);
        assertEquals(id, dominioReconstruido.getId());
        assertEquals("ING1024", dominioReconstruido.getLegajo());
        assertEquals(Puesto.SUPERVISOR, dominioReconstruido.getPuesto());
    }
}