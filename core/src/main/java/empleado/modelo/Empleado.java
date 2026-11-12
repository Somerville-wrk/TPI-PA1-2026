package empleado.modelo;

import empleado.exception.EmpleadoInvalidoException;

import java.time.LocalDate;
import java.util.UUID;

public class Empleado {

    private final UUID id;
    private final String nombreCompleto;
    private final String legajo;
    private final LocalDate fechaIngreso;
    private final Puesto puesto;
    private final Double salario;

    private Empleado(UUID id, String nombreCompleto, String legajo, LocalDate fechaIngreso, Puesto puesto, Double salario) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.legajo = legajo;
        this.fechaIngreso = fechaIngreso;
        this.puesto = puesto;
        this.salario = salario;
    }

    public static Empleado instance(UUID id, String nombreCompleto, String legajo, LocalDate fechaIngreso, Puesto puesto, Double salario) {
        validarDatosObligatorios(id, nombreCompleto, legajo, fechaIngreso, puesto, salario);
        return new Empleado(id, nombreCompleto, legajo, fechaIngreso, puesto, salario);
    }

    private static void validarDatosObligatorios(UUID id, String nombreCompleto, String legajo, LocalDate fechaIngreso, Puesto puesto, Double salario) {
        if (id == null || nombreCompleto == null || nombreCompleto.isBlank() ||
                legajo == null || legajo.isBlank() || fechaIngreso == null ||
                puesto == null || salario == null) {
            throw new EmpleadoInvalidoException("Todos los atributos del empleado son obligatorios");
        }
    }

    public UUID getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getLegajo() { return legajo; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public Puesto getPuesto() { return puesto; }
    public Double getSalario() { return salario; }
}