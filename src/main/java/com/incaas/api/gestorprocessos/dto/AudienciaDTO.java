package com.incaas.api.gestorprocessos.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AudienciaDTO {
    @NotBlank
    private LocalDateTime dataHora;

    @NotBlank
    private String tipo;

    @NotBlank
    private String local;

    @NotBlank
    private Long idProcesso;
}
