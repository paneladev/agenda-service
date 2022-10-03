package pdev.com.agenda.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdev.com.agenda.api.mapper.PacienteMapper;
import pdev.com.agenda.api.request.PacienteRequest;
import pdev.com.agenda.api.response.PacienteCompletoResponse;
import pdev.com.agenda.api.response.PacienteResponse;
import pdev.com.agenda.domain.service.PacienteService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private final PacienteService service;
    private final PacienteMapper mapper;

    @PostMapping
    public ResponseEntity<PacienteResponse> salvar(@Valid @RequestBody PacienteRequest request) {
        Optional<PacienteResponse> optPaciente = Stream.of(request)
                .map(mapper::toPaciente)
                .map(service::salvar)
                .map(mapper::toPacienteResponse)
                .findFirst();
        return ResponseEntity.status(HttpStatus.CREATED).body(optPaciente.get());
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listarTodos() {
        List<PacienteResponse> pacienteResponses = service.listarTodos()
                .stream()
                .map(mapper::toPacienteResponse)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteCompletoResponse> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(mapper::toPacienteCompletoResponse)
                .map(pacienteCompletoResponse -> ResponseEntity.status(HttpStatus.OK).body(pacienteCompletoResponse))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> alterar(@PathVariable Long id, @RequestBody PacienteRequest request) {
        return Stream.of(request)
                .map(mapper::toPaciente)
                .map(paciente -> service.alterar(id, paciente))
                .map(mapper::toPacienteResponse)
                .map(pacienteResponse -> ResponseEntity.status(HttpStatus.OK).body(pacienteResponse))
                .findFirst()
                .get();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}