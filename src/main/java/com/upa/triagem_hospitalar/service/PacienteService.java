package com.upa.triagem_hospitalar.service;

import com.upa.triagem_hospitalar.dto.requestDto.PacienteRequestDto;
import com.upa.triagem_hospitalar.dto.responseDto.PacienteResponseDto;
import com.upa.triagem_hospitalar.entity.Paciente;
import com.upa.triagem_hospitalar.exception.FilaDeAtendimentoVaziaException;
import com.upa.triagem_hospitalar.exception.PacienteJaExisteException;
import com.upa.triagem_hospitalar.exception.PacienteNaoEncontradoException;
import com.upa.triagem_hospitalar.fila.Lista;
import com.upa.triagem_hospitalar.mapStruct.PacienteMapStruct;
import com.upa.triagem_hospitalar.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapStruct mapStruct;
    private final Lista filaTriagem;

    public PacienteService(PacienteRepository pacienteRepository, PacienteMapStruct mapStruct, Lista filaTriagem) {
        this.pacienteRepository = pacienteRepository;
        this.mapStruct = mapStruct;
        this.filaTriagem = filaTriagem;
    }


    @Transactional
    public PacienteResponseDto adicionarPaciente(PacienteRequestDto pacienteRequestDto) {
        Paciente paciente = mapStruct.converterDtoParaPaciente(pacienteRequestDto);

        boolean existePaciente = pacienteRepository.existsByNome(paciente.getNome());

        if (existePaciente) {
            throw new PacienteJaExisteException("Já existe um paciente com o nome " + paciente.getNome());
        }
        pacienteRepository.save(paciente);
        filaTriagem.inserePaciente(paciente);
        return mapStruct.converterParaResponseDto(paciente);

    }

    @Transactional
    public PacienteResponseDto atualizarPaciente(Long id, PacienteRequestDto pacienteRequestDto) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    String nomeAntigo = paciente.getNome();
                    paciente.setNome(pacienteRequestDto.nome());
                    paciente.setPreferencial(pacienteRequestDto.preferencial());
                    Paciente pacienteAtualizado = pacienteRepository.save(paciente);

                    filaTriagem.removerPaciente(nomeAntigo);
                    filaTriagem.atualizarPaciente(mapStruct.converterDtoParaPaciente(pacienteRequestDto).getNome(), paciente);

                    return mapStruct.converterParaResponseDto(pacienteAtualizado);
                }).orElseThrow(() -> new PacienteNaoEncontradoException("Não foi possivel atualizar, paciente não identificado. Id" + id));
    }

    @Transactional
    public void deletarPaciente(long id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() ->
                new PacienteNaoEncontradoException("Não foi possivel deletar! Paciente não identificado. Id" + id));
        filaTriagem.removerPaciente(paciente.getNome());
        pacienteRepository.deleteById(id);
    }

    public PacienteResponseDto buscarPacientePorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado. Id" + id));
        return mapStruct.converterParaResponseDto(paciente);
    }

    public List<PacienteResponseDto> listarPacientes() {
        return mapStruct.converterListParaResponseDto(pacienteRepository.findAll());
    }


    public List<PacienteResponseDto> buscarPacientePorNome(String nome) {
        return mapStruct.converterListParaResponseDto(pacienteRepository.findByNome(nome));
    }

    public List<PacienteResponseDto> listarFila() {
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
