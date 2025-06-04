package com.incaas.api.gestorprocessos.model;

import java.time.LocalDateTime;

import com.incaas.api.gestorprocessos.model.enums.EnumTipoAudiencia;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Audiencia {
    @Id
    private Long id;

    @Column
    private LocalDateTime dataHora;

    @Column
    private String local;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private EnumTipoAudiencia tipoAudiencia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "processo_id", nullable = false)
    private ProcessoJudicial processo;
}
