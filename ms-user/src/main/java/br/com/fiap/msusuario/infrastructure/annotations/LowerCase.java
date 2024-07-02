package br.com.fiap.msusuario.infrastructure.annotations;

import br.com.fiap.msusuario.infrastructure.annotations.validators.LowerCaseValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LowerCaseValidator.class)
public @interface LowerCase {
    String message() default "must contain only lowercase letters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
