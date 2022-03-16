package pdev.com.agenda.domain.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pdev.com.agenda.domain.entity.Paciente;
import pdev.com.agenda.exception.BusinessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PacienteServiceTest {

    @Autowired
    PacienteService service;

    @Test
    void salvar_sucesso() {
        //given
        String nome = "Maria";

        Paciente paciente = new Paciente();
        paciente.setCpf("123");
        paciente.setEmail("email@email.com");
        paciente.setNome(nome);
        paciente.setSobrenome("alfonsina");

        //when
        Paciente pacienteSalvo = service.salvar(paciente);

        //then
        Assertions.assertThat(pacienteSalvo.getId()).isNotNull();
        Assertions.assertThat(pacienteSalvo.getNome()).isEqualTo(nome);
    }

    @Test
    void salvar_cpf_existente() {
        //given
        Paciente paciente = new Paciente();
        paciente.setCpf("234");
        paciente.setEmail("email@email.com");
        paciente.setNome("Maria");
        paciente.setSobrenome("alfonsina");
        service.salvar(paciente);
        Paciente pacienteMesmoCpf = new Paciente();
        pacienteMesmoCpf.setCpf("234");
        pacienteMesmoCpf.setEmail("joao@email.com");
        pacienteMesmoCpf.setNome("Joao");
        pacienteMesmoCpf.setSobrenome("das Rosas");

        //when
        BusinessException businessException = assertThrows(BusinessException.class, () -> service.salvar(pacienteMesmoCpf));

        //then
        Assertions.assertThat("Cpf já cadastrado!").isEqualTo(businessException.getMessage());
    }

    @Test
    void buscar_id_sucesso() {
        Paciente paciente = new Paciente();
        paciente.setCpf("989");
        paciente.setEmail("email@email.com");
        paciente.setNome("Carlos");
        paciente.setSobrenome("Barbosa");
        Paciente pacienteSalvo = service.salvar(paciente);

        Optional<Paciente> optPaciente = service.buscarPorId(pacienteSalvo.getId());

        Assertions.assertThat(optPaciente.get().getId()).isEqualTo(pacienteSalvo.getId());
    }

    @Test
    void buscar_id_nao_encontrado() {
        Long id = 99999L;

        Optional<Paciente> optPaciente = service.buscarPorId(id);

        Assertions.assertThat(optPaciente.isEmpty()).isTrue();
    }

    @Test
    void buscar_todos() {
        Paciente paciente = new Paciente();
        paciente.setCpf("4556");
        paciente.setEmail("email@email.com");
        paciente.setNome("Regina");
        paciente.setSobrenome("Lucia");
        service.salvar(paciente);

        List<Paciente> pacientes = service.listarTodos();

        Assertions.assertThat(pacientes).hasSizeGreaterThan(0);
    }

    @Test
    void delete_sucesso() {
        Paciente paciente = new Paciente();
        paciente.setCpf("4556");
        paciente.setEmail("email@email.com");
        paciente.setNome("Regina");
        paciente.setSobrenome("Lucia");
        Paciente pacienteSalvo = service.salvar(paciente);

        service.deletar(pacienteSalvo.getId());

        Optional<Paciente> optPaciente = service.buscarPorId(pacienteSalvo.getId());
        Assertions.assertThat(optPaciente.isEmpty()).isTrue();
    }
}