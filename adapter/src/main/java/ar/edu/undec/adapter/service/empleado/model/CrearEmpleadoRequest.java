package ar.edu.undec.adapter.service.empleado.model;

import empleado.modelo.Puesto;

import java.time.LocalDate;

public record CrearEmpleadoRequest(
        String nombreCompleto,
        String legajo,
        LocalDate fechaIngreso,
        Puesto puesto,
        Double salario
) {
}