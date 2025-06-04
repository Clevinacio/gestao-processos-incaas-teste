package com.incaas.api.gestorprocessos.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumStatus {
    ATIVO("Ativo"),
    ARQUIVADO("Arquivado"),
    SUSPENSO("Suspenso");

    private String status;

    @JsonValue
    public String getStatus() {
        return status;
    }

    EnumStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static EnumStatus fromStatus(String status) {
        for (EnumStatus enumStatus : EnumStatus.values()) {
            if (enumStatus.getStatus().equalsIgnoreCase(status)) {
                return enumStatus;
            }
        }
        throw new IllegalArgumentException("Status desconhecido: " + status);
    }
}
