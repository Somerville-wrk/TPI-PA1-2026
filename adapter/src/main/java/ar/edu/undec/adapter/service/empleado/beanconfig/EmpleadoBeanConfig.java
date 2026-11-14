package ar.edu.undec.adapter.service.empleado.beanconfig;

import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.input.CrearEmpleadoInput;
import empleado.input.ListarEmpleadosInput;
import empleado.output.EmpleadoRepositoryPort;
import empleado.usecase.BuscarEmpleadoPorLegajoUseCase;
import empleado.usecase.CrearEmpleadoUseCase;
import empleado.usecase.ListarEmpleadosUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmpleadoBeanConfig {

    @Bean
    public CrearEmpleadoInput crearEmpleadoInput(EmpleadoRepositoryPort empleadoRepositoryPort) {
        return new CrearEmpleadoUseCase(empleadoRepositoryPort);
    }

    @Bean
    public BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput(EmpleadoRepositoryPort empleadoRepositoryPort) {
        return new BuscarEmpleadoPorLegajoUseCase(empleadoRepositoryPort);
    }

    @Bean
    public ListarEmpleadosInput listarEmpleadosInput(EmpleadoRepositoryPort empleadoRepositoryPort) {
        return new ListarEmpleadosUseCase(empleadoRepositoryPort);
    }
}