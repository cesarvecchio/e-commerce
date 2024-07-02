package br.com.fiap.msusuario.infrastructure.validations;

import lombok.Builder;

@Builder
public record ValidationError(String field, String errorMessage) {
}

