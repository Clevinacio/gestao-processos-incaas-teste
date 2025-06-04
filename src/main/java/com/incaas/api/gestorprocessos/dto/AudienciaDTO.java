package com.incaas.api.gestorprocessos.dto;

import java.time.LocalDateTime;

import com.incaas.api.gestorprocessos.model.enums.EnumTipoAudiencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AudienciaDTO {
    @NotNull
    private LocalDateTime dataHora;

    @NotNull
    private EnumTipoAudiencia tipo;

    @NotBlank
    private String local;
}
