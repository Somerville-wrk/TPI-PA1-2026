package ar.edu.undec.adapter.service.empleado.model;

import empleado.modelo.Puesto;

import java.time.LocalDate;
import java.util.UUID;

public record EmpleadoResponse(
        UUID id,
        String nombreCompleto,
        String legajo,
        LocalDate fechaIngreso,
        Puesto puesto,
        Double salario
) {
}