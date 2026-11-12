package empleado.modelo;

import empleado.exception.EmpleadoInvalidoException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.SalarioInvalidoException;

import java.time.LocalDate;
import java.util.UUID;

public class Empleado {

    private static final String FORMATO_LEGAJO_REGEX = "^[A-Z]{3}\\d{4}$";

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
        validarFormatoLegajo(legajo);
        validarFechaIngreso(fechaIngreso);
        validarSalario(salario, puesto);
        return new Empleado(id, nombreCompleto, legajo, fechaIngreso, puesto, salario);
    }

    private static void validarDatosObligatorios(UUID id, String nombreCompleto, String legajo, LocalDate fechaIngreso, Puesto puesto, Double salario) {
        if (id == null || nombreCompleto == null || nombreCompleto.isBlank() ||
                legajo == null || legajo.isBlank() || fechaIngreso == null ||
                puesto == null || salario == null) {
            throw new EmpleadoInvalidoException("Todos los atributos del empleado son obligatorios");
        }
    }

    private static void validarFormatoLegajo(String legajo) {
        if (!legajo.matches(FORMATO_LEGAJO_REGEX)) {
            throw new LegajoInvalidoException("El legajo debe tener 3 letras mayusculas seguidas de 4 numeros");
        }
    }

    private static void validarFechaIngreso(LocalDate fechaIngreso) {
        if (fechaIngreso.isAfter(LocalDate.now())) {
            throw new FechaIngresoInvalidaException("La fecha de ingreso no puede ser posterior a la fecha actual");
        }
    }

    private static void validarSalario(Double salario, Puesto puesto) {
        if (salario <= 0 || salario < puesto.getSalarioMinimo()) {
            throw new SalarioInvalidoException("El salario debe ser mayor a cero y alcanzar el minimo del puesto " + puesto);
        }
    }

    public UUID getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getLegajo() { return legajo; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public Puesto getPuesto() { return puesto; }
    public Double getSalario() { return salario; }
}