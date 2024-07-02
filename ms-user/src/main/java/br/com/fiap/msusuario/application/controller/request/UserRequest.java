package br.com.fiap.msusuario.application.controller.request;


import br.com.fiap.msusuario.domain.entity.enums.UserRole;
import br.com.fiap.msusuario.infrastructure.annotations.LowerCase;
import jakarta.validation.constraints.NotNull;

public record UserRequest(@NotNull @LowerCase String login, @NotNull String password, UserRole role) {
}
