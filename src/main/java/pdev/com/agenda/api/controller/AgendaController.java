package pdev.com.agenda.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdev.com.agenda.api.mapper.AgendaMapper;
import pdev.com.agenda.api.request.AgendaRequest;
import pdev.com.agenda.api.response.AgendaResponse;
import pdev.com.agenda.domain.entity.Agenda;
import pdev.com.agenda.domain.service.AgendaService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private final AgendaService service;
    private final AgendaMapper mapper;

    @GetMapping
    public ResponseEntity<List<AgendaResponse>> buscarTodos() {
        List<Agenda> agendas = service.listarTodos();
        List<AgendaResponse> agendaResponses = mapper.toAgendaResponseList(agendas);

        return ResponseEntity.status(HttpStatus.OK).body(agendaResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponse> buscarPorId(@PathVariable Long id) {
        Optional<Agenda> optAgenda = service.buscarPorId(id);

        if(optAgenda.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AgendaResponse agendaResponse = mapper.toAgendaResponse(optAgenda.get());

        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);
    }

    @PostMapping
    public ResponseEntity<AgendaResponse> salvar(@Valid @RequestBody AgendaRequest request) {
        Agenda agenda = mapper.toAgenda(request);
        Agenda agendaSalva = service.salvar(agenda);
        AgendaResponse agendaResponse = mapper.toAgendaResponse(agendaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaResponse);
    }
}
