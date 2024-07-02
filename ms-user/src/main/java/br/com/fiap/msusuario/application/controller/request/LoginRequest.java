package br.com.fiap.msusuario.application.controller.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(@NotNull String login, @NotNull String password) {
}
