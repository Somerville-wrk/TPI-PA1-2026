package ar.edu.undec.adapter.service.empleado.controller;

import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoServiceMapper;
import ar.edu.undec.adapter.service.empleado.model.CrearEmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.input.CrearEmpleadoInput;
import empleado.input.ListarEmpleadosInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final CrearEmpleadoInput crearEmpleadoInput;
    private final BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput;
    private final ListarEmpleadosInput listarEmpleadosInput;
    private final EmpleadoServiceMapper empleadoServiceMapper;

    public EmpleadoController(CrearEmpleadoInput crearEmpleadoInput,
                              BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput,
                              ListarEmpleadosInput listarEmpleadosInput,
                              EmpleadoServiceMapper empleadoServiceMapper) {
        this.crearEmpleadoInput = crearEmpleadoInput;
        this.buscarEmpleadoPorLegajoInput = buscarEmpleadoPorLegajoInput;
        this.listarEmpleadosInput = listarEmpleadosInput;
        this.empleadoServiceMapper = empleadoServiceMapper;
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> crearEmpleado(@RequestBody CrearEmpleadoRequest request) {
        return null;
    }
}