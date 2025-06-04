package com.incaas.api.gestorprocessos.model;

public enum StatusEnum {
    ATIVO("Ativo"),
    ARQUIVADO("Arquivado"),
    SUSPENSO("Suspenso");

    private String status;

    public String getStatus() {
        return status;
    }

    StatusEnum(String status) {
        this.status = status;
    }
}
