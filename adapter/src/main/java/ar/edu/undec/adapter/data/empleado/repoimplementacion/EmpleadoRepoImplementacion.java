package ar.edu.undec.adapter.data.empleado.repoimplementacion;

import ar.edu.undec.adapter.data.empleado.crud.EmpleadoCrudRepository;
import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoDataMapper;
import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
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
        return empleadoCrudRepository.existsByLegajo(legajo);
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        EmpleadoEntity entity = empleadoDataMapper.toEntity(empleado);
        EmpleadoEntity guardado = empleadoCrudRepository.save(entity);
        return empleadoDataMapper.toDomain(guardado);
    }

    @Override
    public Optional<Empleado> buscarPorLegajo(String legajo) {
        return empleadoCrudRepository.findByLegajo(legajo)
                .map(empleadoDataMapper::toDomain);
    }

    @Override
    public List<Empleado> obtenerTodos() {
        return List.of();
    }
}