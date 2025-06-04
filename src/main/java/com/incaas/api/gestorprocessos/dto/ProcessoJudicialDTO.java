package com.incaas.api.gestorprocessos.dto;

import com.incaas.api.gestorprocessos.validation.NumeroProcessoValido;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessoJudicialDTO {
    @NumeroProcessoValido
    private String numeroProcesso;

    @NotBlank
    private String vara;

    @NotBlank
    private String comarca;

    @NotBlank
    private String assunto;

    @NotBlank
    private String status;

}
