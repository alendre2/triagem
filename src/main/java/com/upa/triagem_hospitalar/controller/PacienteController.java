package com.upa.triagem_hospitalar.controller;

import com.upa.triagem_hospitalar.dto.requestDto.PacienteRequestDto;
import com.upa.triagem_hospitalar.dto.responseDto.PacienteResponseDto;
import com.upa.triagem_hospitalar.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin("*")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDto> adicionarPaciente(@Valid @RequestBody PacienteRequestDto pacienteRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.adicionarPaciente(pacienteRequestDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PacienteResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody PacienteRequestDto pacienteRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.atualizarPaciente(id, pacienteRequestDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        pacienteService.deletarPaciente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PacienteResponseDto> buscarPacientePorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.buscarPacientePorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDto>> listarPacientes(@RequestParam(value = "nome", required = false) String nome) {
        if (nome != null && !nome.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(pacienteService.buscarPacientePorNome(nome));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(pacienteService.listarPacientes());
        }
    }

    @GetMapping("/triagem")
    public ResponseEntity<List<PacienteResponseDto>> listarFila() {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.listarFila());
    }

    @GetMapping("/proximo")
    public ResponseEntity<PacienteResponseDto> proximoPaciente() {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.proximoPaciente());
    }
}