package com.upa.triagem_hospitalar.service;

import java.util.List;
import java.util.Optional;

import com.upa.triagem_hospitalar.dto.requestDto.PacienteRequestDto;
import com.upa.triagem_hospitalar.dto.responseDto.PacienteResponseDto;
import com.upa.triagem_hospitalar.mapStruct.PacienteMapStruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upa.triagem_hospitalar.entity.Paciente;
import com.upa.triagem_hospitalar.exception.FilaDeAtendimentoVaziaException;
import com.upa.triagem_hospitalar.exception.PacienteNaoEncontradoException;
import com.upa.triagem_hospitalar.fila.Lista; // A lista personalizada
import com.upa.triagem_hospitalar.repository.PacienteRepository;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapStruct mapStruct;
    private final Lista filaTriagem = new Lista();

    public PacienteService(PacienteRepository pacienteRepository, PacienteMapStruct mapStruct) {
        this.pacienteRepository = pacienteRepository;
        this.mapStruct = mapStruct;
    }


    @Transactional
    public PacienteResponseDto adicionarPaciente(PacienteRequestDto pacienteRequestDto) {
        Paciente paciente = mapStruct.converterDtoParaPaciente(pacienteRequestDto);
        pacienteRepository.save(paciente);
        filaTriagem.inserePaciente(paciente);
        return mapStruct.converterParaResponseDto(paciente);

    }

    @Transactional
    public PacienteResponseDto atualizarPaciente(Long id, PacienteRequestDto pacienteRequestDto) {
        return pacienteRepository.findById(id)
                .map(paciente ->{
                    paciente.setNome(pacienteRequestDto.nome());
                    paciente.setPreferencial(pacienteRequestDto.preferencial());
                    Paciente pacienteAtualizado = pacienteRepository.save(paciente);
                    return mapStruct.converterParaResponseDto(pacienteAtualizado);
                }).orElseThrow(() -> new PacienteNaoEncontradoException("Não foi possivel atualizar, paciente não identificado. Id"+ id  ));
    }

    @Transactional
    public void deletarPaciente(long id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(()->
                new PacienteNaoEncontradoException("Não foi possivel deletar! Paciente não identificado. Id"+ id  ));
        filaTriagem.removerPaciente(paciente.getNome());
        pacienteRepository.deleteById(id);
    }

    public PacienteResponseDto buscarPacientePorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado. Id"+ id  ));
        return mapStruct.converterParaResponseDto(paciente);
    }

    public List<PacienteResponseDto> listarPacientes() {
        return mapStruct.converterListParaResponseDto(pacienteRepository.findAll());
    }


    public List<PacienteResponseDto> buscarPacientePorNome(String nome) {
        return mapStruct.converterListParaResponseDto(pacienteRepository.findByNomeContainingIgnoreCase(nome));
    }

    public List<PacienteResponseDto> listarFila(){
        return mapStruct.converterListParaResponseDto(filaTriagem.toList());
    }

    // Chamar o próximo paciente da fila de triagem
    public PacienteResponseDto proximoPaciente() {
        Paciente proximo = filaTriagem.removerPrimeiro();
        if (proximo == null) {
            throw new FilaDeAtendimentoVaziaException("Fila de atendimento vazia.");
        }
        return mapStruct.converterParaResponseDto(proximo);
    }
}
