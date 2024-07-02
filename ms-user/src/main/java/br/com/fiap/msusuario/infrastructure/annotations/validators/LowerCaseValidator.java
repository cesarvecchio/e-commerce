package br.com.fiap.msusuario.infrastructure.annotations.validators;

import br.com.fiap.msusuario.infrastructure.annotations.LowerCase;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LowerCaseValidator implements ConstraintValidator<LowerCase, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals(value.toLowerCase());
    }
}

