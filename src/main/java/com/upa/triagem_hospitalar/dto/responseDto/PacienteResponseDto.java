package com.upa.triagem_hospitalar.dto.responseDto;

import jakarta.validation.constraints.NotBlank;

public record PacienteResponseDto(@NotBlank String nome,
                                  boolean preferencial) {
}
