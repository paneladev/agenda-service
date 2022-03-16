package pdev.com.agenda.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pdev.com.agenda.api.request.PacienteRequest;
import pdev.com.agenda.domain.entity.Paciente;
import pdev.com.agenda.domain.service.PacienteService;
import pdev.com.agenda.exception.BusinessException;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PacienteControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PacienteService pacienteService;

    Paciente paciente;
    PacienteRequest pacienteRequest;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Teste");
        paciente.setSobrenome("da Silva");
        paciente.setEmail("paciente@email.com");
        paciente.setCpf("123");

        pacienteRequest = new PacienteRequest();
        pacienteRequest.setNome("Novo");
        pacienteRequest.setSobrenome("paciente");
        pacienteRequest.setCpf("234");
    }

    @Test
    void lista_paciente_por_id() throws Exception {
        BDDMockito.given(pacienteService.buscarPorId(ArgumentMatchers.any())).willReturn(Optional.of(paciente));

        mockMvc.perform(MockMvcRequestBuilders.get("/paciente/" + paciente.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(paciente.getId()));
    }

    @Test
    void lista_paciente_nao_encontrado() throws Exception {
        BDDMockito.given(pacienteService.buscarPorId(ArgumentMatchers.any())).willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/paciente/" + paciente.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void lista_todos_pacientes() throws Exception {
        BDDMockito.given(pacienteService.listarTodos()).willReturn(List.of(paciente));

        mockMvc.perform(MockMvcRequestBuilders.get("/paciente"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(paciente.getId()));

    }

    @Test
    void salvar_paciente() throws Exception {

        PacienteRequest paciente = this.pacienteRequest;
        String pacienteRequest = objectMapper.writeValueAsString(paciente);
        BDDMockito.given(pacienteService.salvar(ArgumentMatchers.any())).willReturn(this.paciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(pacienteRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void alterar_paciente() throws Exception {
        PacienteRequest paciente = this.pacienteRequest;
        String pacienteRequest = objectMapper.writeValueAsString(paciente);
        BDDMockito.given(pacienteService.alterar(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(this.paciente);

        mockMvc.perform(MockMvcRequestBuilders.put("/paciente/" + this.paciente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(pacienteRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void deletar_paciente() throws Exception {
        BDDMockito.willDoNothing().given(pacienteService);

        mockMvc.perform(MockMvcRequestBuilders.delete("/paciente/" + this.paciente.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}