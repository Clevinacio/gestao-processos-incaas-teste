package com.incaas.api.gestorprocessos.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumTipoAudiencia {
    AUDIENCIA_CONCILIACAO("Conciliação"),
    AUDIENCIA_INSTRUCAO("Instrução"),
    AUDIENCIA_JULGAMETNO("Julgamento");

    private String descricao;

    EnumTipoAudiencia(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static EnumTipoAudiencia fromDescricao(String descricao) {
        for (EnumTipoAudiencia tipo : EnumTipoAudiencia.values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de audiência desconhecido: " + descricao);
    }
}
