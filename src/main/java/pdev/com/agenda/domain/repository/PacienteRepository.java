package pdev.com.agenda.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pdev.com.agenda.domain.entity.PacienteAgenda;
import pdev.com.agenda.domain.entity.Paciente;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByCpf(String cpf);

    @Query("SELECT p FROM Paciente p WHERE p.cpf = ?1")
    Optional<Paciente> buscarPorCpf(String cpf);

    @Query(value = "SELECT * FROM Paciente p WHERE p.cpf = ?1", nativeQuery = true)
    Optional<Paciente> buscarPorCpfNative(String cpf);

    @Query(value = "select p.nome, p.cpf, a.data_hora as horario, a.descricao from paciente p, agenda a where p.id = a.paciente_id", nativeQuery = true)
    List<PacienteAgenda> findAllAgendamentos();
}