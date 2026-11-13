package ar.edu.undec.adapter;

import ar.edu.undec.adapter.service.empleado.model.CrearEmpleadoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import empleado.modelo.Puesto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
class EmpleadoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("E2E: Crea un empleado en BD real, lo consulta por legajo y lo lista")
    void flujoCompleto_crearBuscarYListarEmpleado_persisteYRecuperaDeBaseDeDatosReal() throws Exception {
        Puesto puestoValido = Puesto.values()[0];

        CrearEmpleadoRequest request = new CrearEmpleadoRequest(
                "Juan Perez",
                "ABC1234",
                LocalDate.now().minusYears(2),
                puestoValido,
                850000.0
        );
        String requestJson = objectMapper.writeValueAsString(request);

        // 1. POST /empleados -> Pasa por Controller, UseCase, Repo y persiste en H2
        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"))
                .andExpect(jsonPath("$.legajo").value("ABC1234"))
                .andExpect(jsonPath("$.puesto").value(puestoValido.name()))
                .andExpect(jsonPath("$.salario").value(850000.0));

        // 2. GET /empleados/{legajo} -> Recupera el registro real desde H2
        mockMvc.perform(get("/empleados/ABC1234")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legajo").value("ABC1234"))
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"));

        // 3. GET /empleados -> Verifica que la lista contenga el empleado persistido
        mockMvc.perform(get("/empleados")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].legajo").value("ABC1234"));
    }

    @Test
    @DisplayName("E2E: Rechaza legajo duplicado contra BD real con 409 Conflict")
    void crearEmpleado_cuandoLegajoYaExisteEnBaseDeDatosReal_retorna409Conflict() throws Exception {
        Puesto puestoValido = Puesto.values()[0];

        CrearEmpleadoRequest request = new CrearEmpleadoRequest(
                "Ana Gomez",
                "DUP9999",
                LocalDate.now().minusYears(1),
                puestoValido,
                900000.0
        );
        String requestJson = objectMapper.writeValueAsString(request);

        // Primer alta: persiste en H2 con 201 Created
        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());

        // Segundo alta con mismo legajo: el caso de uso detecta colision en H2 y lanza 409 Conflict
        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje", notNullValue()));
    }

    @Test
    @DisplayName("E2E: Retorna 404 Not Found cuando se consulta un legajo inexistente en BD real")
    void buscarPorLegajo_cuandoNoExisteEnBaseDeDatosReal_retorna404NotFound() throws Exception {
        mockMvc.perform(get("/empleados/NOE9999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje", notNullValue()));
    }

    @Test
    @DisplayName("E2E: Retorna 400 Bad Request cuando falla una regla de negocio del dominio")
    void crearEmpleado_cuandoSalarioEsNegativo_retorna400BadRequest() throws Exception {
        Puesto puestoValido = Puesto.values()[0];

        CrearEmpleadoRequest requestInvalido = new CrearEmpleadoRequest(
                "Carlos Lopez",
                "ERR0001",
                LocalDate.now().minusMonths(6),
                puestoValido,
                -500.0
        );
        String requestInvalidoJson = objectMapper.writeValueAsString(requestInvalido);

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInvalidoJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje", notNullValue()));
    }
}