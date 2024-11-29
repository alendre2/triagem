package com.upa.triagem_hospitalar.controller;

import java.util.List;
import java.util.Optional;

import com.upa.triagem_hospitalar.dto.requestDto.PacienteRequestDto;
import com.upa.triagem_hospitalar.dto.responseDto.PacienteResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.upa.triagem_hospitalar.entity.Paciente;
import com.upa.triagem_hospitalar.service.PacienteService;

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
    public ResponseEntity<PacienteResponseDto> atualizar (@PathVariable Long id,@Valid @RequestBody PacienteRequestDto pacienteRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.atualizarPaciente(id,pacienteRequestDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id){
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
    public ResponseEntity<List<PacienteResponseDto>> listarFila(){
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.listarFila());
    }

    @GetMapping("/proximo")
    public ResponseEntity<PacienteResponseDto> proximoPaciente() {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.proximoPaciente());
    }
}