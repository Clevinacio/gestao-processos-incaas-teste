package com.incaas.api.gestorprocessos.model;

import java.time.LocalDateTime;

import com.incaas.api.gestorprocessos.model.enums.EnumTipoAudiencia;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Audiencia {
    private Long id;
    private LocalDateTime dataHora;
    private String local;
    private EnumTipoAudiencia tipoAudiencia;
    private ProcessoJudicial processoJudicial;
}
