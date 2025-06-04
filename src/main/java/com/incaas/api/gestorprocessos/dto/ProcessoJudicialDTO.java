package com.incaas.api.gestorprocessos.dto;

import com.incaas.api.gestorprocessos.model.enums.EnumStatus;
import com.incaas.api.gestorprocessos.validation.NumeroProcessoValido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoJudicialDTO {
    @NumeroProcessoValido
    private String numeroProcesso;

    @NotBlank
    private String vara;

    @NotBlank
    private String comarca;

    @NotBlank
    private String assunto;

    @NotNull
    private EnumStatus status;

}
