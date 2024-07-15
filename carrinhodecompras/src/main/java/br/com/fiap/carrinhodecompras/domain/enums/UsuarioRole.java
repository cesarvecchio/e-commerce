package br.com.fiap.carrinhodecompras.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsuarioRole {
    ADMIN("admin"),
    USER("user");

    private final String role;;
}
