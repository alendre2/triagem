package com.upa.triagem_hospitalar.dto.requestDto;

import jakarta.validation.constraints.NotBlank;

public record PacienteRequestDto(@NotBlank String nome,
                                 boolean preferencial) {
}