package com.incaas.api.gestorprocessos.model.enums;

public enum StatusEnum {
    ATIVO("Ativo"),
    ARQUIVADO("Arquivado"),
    SUSPENSO("Suspenso");

    @SuppressWarnings("unused")
    private String status;

    StatusEnum(String status) {
        this.status = status;
    }
}
