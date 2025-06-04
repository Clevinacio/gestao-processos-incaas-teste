package com.incaas.api.gestorprocessos.model;

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
