package com.incaas.api.gestorprocessos.validation;

import jakarta.validation.ConstraintValidator;

public class NumeroProcessoValidoValidator implements ConstraintValidator<NumeroProcessoValido, String> {

    private static final String NUMERO_PROCESSO_REGEX = "^\\d{7}-\\d{2}\\.\\d{4}\\.\\d\\.\\d{2}\\.\\d{4}$";

    @Override
    public boolean isValid(String numeroProcesso, jakarta.validation.ConstraintValidatorContext context) {
        return numeroProcesso != null && numeroProcesso.matches(NUMERO_PROCESSO_REGEX);
    }

}
