package ar.edu.undec.adapter.service.empleado;

import ar.edu.undec.adapter.service.empleado.controller.EmpleadoController;
import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoServiceMapper;
import ar.edu.undec.adapter.service.empleado.model.CrearEmpleadoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.input.CrearEmpleadoCommand;
import empleado.input.CrearEmpleadoInput;
import empleado.input.ListarEmpleadosInput;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpleadoController.class)
@Import(EmpleadoServiceMapper.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CrearEmpleadoInput crearEmpleadoInput;

    @MockBean
    private BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput;

    @MockBean
    private ListarEmpleadosInput listarEmpleadosInput;

    @Test
    void crearEmpleado_cuandoDatosSonValidos_retorna201CreatedYEmpleadoResponse() throws Exception {
        UUID idGenerado = UUID.randomUUID();
        LocalDate fechaIngreso = LocalDate.now().minusMonths(2);

        CrearEmpleadoRequest request = new CrearEmpleadoRequest(
                "Camila Fernandez",
                "ING1024",
                fechaIngreso,
                Puesto.SUPERVISOR,
                850000.0
        );

        Empleado empleadoCreado = Empleado.instance(
                idGenerado,
                "Camila Fernandez",
                "ING1024",
                fechaIngreso,
                Puesto.SUPERVISOR,
                850000.0
        );

        when(crearEmpleadoInput.crearEmpleado(any(CrearEmpleadoCommand.class))).thenReturn(empleadoCreado);

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(idGenerado.toString()))
                .andExpect(jsonPath("$.nombreCompleto").value("Camila Fernandez"))
                .andExpect(jsonPath("$.legajo").value("ING1024"))
                .andExpect(jsonPath("$.puesto").value("SUPERVISOR"))
                .andExpect(jsonPath("$.salario").value(850000.0));
    }

    @Test
    void buscarPorLegajo_cuandoEmpleadoExiste_retorna200OkYEmpleadoResponse() throws Exception {
        UUID idExistente = UUID.randomUUID();
        Empleado empleadoEncontrado = Empleado.instance(
                idExistente,
                "Rodolfo Gaston Somerville",
                "EMP1234",
                LocalDate.now().minusYears(1),
                Puesto.GERENTE,
                1500000.0
        );

        when(buscarEmpleadoPorLegajoInput.buscarPorLegajo("EMP1234")).thenReturn(empleadoEncontrado);

        mockMvc.perform(get("/empleados/EMP1234")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idExistente.toString()))
                .andExpect(jsonPath("$.nombreCompleto").value("Rodolfo Gaston Somerville"))
                .andExpect(jsonPath("$.legajo").value("EMP1234"))
                .andExpect(jsonPath("$.puesto").value("GERENTE"))
                .andExpect(jsonPath("$.salario").value(1500000.0));
    }

    @Test
    void listarEmpleados_cuandoExistenEmpleados_retorna200OkYListaDeResponses() throws Exception {
        Empleado emp1 = Empleado.instance(
                UUID.randomUUID(), "Ana Lopez", "ANA1001", LocalDate.now().minusMonths(6), Puesto.ANALISTA, 550000.0);
        Empleado emp2 = Empleado.instance(
                UUID.randomUUID(), "Carlos Ruiz", "CAR2002", LocalDate.now().minusYears(2), Puesto.SUPERVISOR, 900000.0);

        when(listarEmpleadosInput.listarEmpleados()).thenReturn(List.of(emp1, emp2));

        mockMvc.perform(get("/empleados")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].legajo").value("ANA1001"))
                .andExpect(jsonPath("$[1].legajo").value("CAR2002"));
    }
}