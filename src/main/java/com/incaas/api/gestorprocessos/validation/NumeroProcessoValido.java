package com.incaas.api.gestorprocessos.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NumeroProcessoValidoValidator.class)
@Target({ ElementType.FIELD })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface NumeroProcessoValido {
    String message() default "Número do processo inválido. Use o formato 0000000-00.0000.0.00.0000";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
