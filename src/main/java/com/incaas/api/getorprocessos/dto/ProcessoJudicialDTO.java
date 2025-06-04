package com.incaas.api.getorprocessos.dto;

import com.incaas.api.getorprocessos.validation.NumeroProcessoValido;

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

}
