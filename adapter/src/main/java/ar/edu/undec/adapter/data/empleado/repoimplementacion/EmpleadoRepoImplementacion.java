package ar.edu.undec.adapter.data.empleado.repoimplementacion;

import ar.edu.undec.adapter.data.empleado.crud.EmpleadoCrudRepository;
import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoDataMapper;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmpleadoRepoImplementacion implements EmpleadoRepositoryPort {

    private final EmpleadoCrudRepository empleadoCrudRepository;
    private final EmpleadoDataMapper empleadoDataMapper;

    public EmpleadoRepoImplementacion(EmpleadoCrudRepository empleadoCrudRepository,
                                      EmpleadoDataMapper empleadoDataMapper) {
        this.empleadoCrudRepository = empleadoCrudRepository;
        this.empleadoDataMapper = empleadoDataMapper;
    }

    @Override
    public boolean existePorLegajo(String legajo) {
        return false;
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        return null;
    }

    @Override
    public Optional<Empleado> buscarPorLegajo(String legajo) {
        return Optional.empty();
    }

    @Override
    public List<Empleado> obtenerTodos() {
        return List.of();
    }
}