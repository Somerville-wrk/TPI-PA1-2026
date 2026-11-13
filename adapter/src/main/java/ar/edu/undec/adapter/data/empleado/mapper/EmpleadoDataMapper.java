package ar.edu.undec.adapter.data.empleado.mapper;

import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
import empleado.modelo.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoDataMapper {

    public EmpleadoEntity toEntity(Empleado empleado) {
        return new EmpleadoEntity(
                empleado.getId(),
                empleado.getNombreCompleto(),
                empleado.getLegajo(),
                empleado.getFechaIngreso(),
                empleado.getPuesto(),
                empleado.getSalario()
        );
    }

    public Empleado toDomain(EmpleadoEntity entity) {
        return Empleado.instance(
                entity.getId(),
                entity.getNombreCompleto(),
                entity.getLegajo(),
                entity.getFechaIngreso(),
                entity.getPuesto(),
                entity.getSalario()
        );
    }
}