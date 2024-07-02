package br.com.fiap.msusuario.application.controller.response;

import br.com.fiap.msusuario.domain.entity.enums.UserRole;
import lombok.Builder;

@Builder
public record UserResponse(String id, String login, String password, UserRole role) {
}
