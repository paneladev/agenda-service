package pdev.com.agenda.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdev.com.agenda.domain.entity.Agenda;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    Optional<Agenda> findByHorario(LocalDateTime horario);
}
