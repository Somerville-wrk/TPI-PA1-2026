package ar.edu.undec.adapter.service.empleado.mapper;

import ar.edu.undec.adapter.service.empleado.model.CrearEmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.input.CrearEmpleadoCommand;
import empleado.modelo.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoServiceMapper {

    public CrearEmpleadoCommand toCommand(CrearEmpleadoRequest request) {
        return new CrearEmpleadoCommand(
                request.nombreCompleto(),
                request.legajo(),
                request.fechaIngreso(),
                request.puesto(),
                request.salario()
        );
    }

    public EmpleadoResponse toResponse(Empleado empleado) {
        return new EmpleadoResponse(
                empleado.getId(),
                empleado.getNombreCompleto(),
                empleado.getLegajo(),
                empleado.getFechaIngreso(),
                empleado.getPuesto(),
                empleado.getSalario()
        );
    }
}