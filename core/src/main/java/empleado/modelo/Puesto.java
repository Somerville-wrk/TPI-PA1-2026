package empleado.modelo;

public enum Puesto {
    ANALISTA(500000.0),
    SUPERVISOR(800000.0),
    GERENTE(1200000.0);

    private final double salarioMinimo;

    Puesto(double salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
    }

    public double getSalarioMinimo() {
        return salarioMinimo;
    }
}