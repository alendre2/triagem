package com.upa.triagem_hospitalar.mapStruct;

import com.upa.triagem_hospitalar.dto.requestDto.PacienteRequestDto;
import com.upa.triagem_hospitalar.dto.responseDto.PacienteResponseDto;
import com.upa.triagem_hospitalar.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PacienteMapStruct {

    @Mapping(target = "id", ignore = true)
    Paciente converterDtoParaPaciente(PacienteRequestDto pacienteRequestDto);

    PacienteResponseDto converterParaResponseDto(Paciente paciente);

    List<PacienteResponseDto> converterListParaResponseDto(List<Paciente> pacientes);
}
