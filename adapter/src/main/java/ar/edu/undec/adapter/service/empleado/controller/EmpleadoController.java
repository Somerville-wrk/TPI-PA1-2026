package ar.edu.undec.adapter.service.empleado.controller;

import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoServiceMapper;
import ar.edu.undec.adapter.service.empleado.model.CrearEmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.input.CrearEmpleadoCommand;
import empleado.input.CrearEmpleadoInput;
import empleado.input.ListarEmpleadosInput;
import empleado.modelo.Empleado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        CrearEmpleadoCommand command = empleadoServiceMapper.toCommand(request);
        Empleado empleadoCreado = crearEmpleadoInput.crearEmpleado(command);
        EmpleadoResponse response = empleadoServiceMapper.toResponse(empleadoCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<EmpleadoResponse> buscarPorLegajo(@PathVariable("legajo") String legajo) {
        Empleado empleado = buscarEmpleadoPorLegajoInput.buscarPorLegajo(legajo);
        EmpleadoResponse response = empleadoServiceMapper.toResponse(empleado);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> listarEmpleados() {
        List<EmpleadoResponse> responses = listarEmpleadosInput.listarEmpleados()
                .stream()
                .map(empleadoServiceMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }
}