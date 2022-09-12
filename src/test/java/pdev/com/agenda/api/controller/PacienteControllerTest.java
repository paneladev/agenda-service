package pdev.com.agenda.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pdev.com.agenda.api.request.PacienteRequest;
import pdev.com.agenda.domain.entity.Paciente;
import pdev.com.agenda.domain.repository.PacienteRepository;
import pdev.com.agenda.exception.BusinessException;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PacienteControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PacienteRepository repository;

    @BeforeEach
    void up() {
        Paciente paciente = new Paciente();
        paciente.setNome("Maria");
        paciente.setSobrenome("Leopoldina");
        paciente.setCpf("123");
        paciente.setEmail("maria@mail.com");
        repository.save(paciente);
    }

    @AfterEach
    void down() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Listar todos pacientes")
    void listaPacientes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/paciente"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Listar paciente por id")
    void listaPacientePorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/paciente/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Salva paciente com sucesso")
    void salvarPaciente() throws Exception {
        PacienteRequest paciente = PacienteRequest.builder()
                .email("joao@mail.com")
                .nome("joao")
                .sobrenome("silva")
                .cpf("234")
                .build();

        String pacienteRequest = mapper.writeValueAsString(paciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(pacienteRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Salva paciente com sucesso")
    void salvarPacienteComCpfExistente() throws Exception {
        PacienteRequest paciente = PacienteRequest.builder()
                .email("pedro@mail.com")
                .nome("pedro")
                .sobrenome("silva")
                .cpf("123")
                .build();

        String pacienteRequest = mapper.writeValueAsString(paciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(pacienteRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof BusinessException))
                .andDo(MockMvcResultHandlers.print());
    }
}