package com.incaas.api.gestorprocessos.model.enums;

public enum EnumTipoAudiencia {
    AUDIENCIA_CONCILIACAO("Conciliação"),
    AUDIENCIA_INSTRUCAO("Instrução"),
    AUDIENCIA_JULGAMETNO("Julgamento");

    private String descricao;

    EnumTipoAudiencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
