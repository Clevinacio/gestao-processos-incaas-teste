package com.incaas.api.gestorprocessos.model;

import com.incaas.api.gestorprocessos.model.enums.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoJudicial {
    private String numeroProcesso;
    private String vara;
    private String comarca;
    private String assunto;
    private StatusEnum status;
}
