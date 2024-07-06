package br.com.fiap.mspagamento.interfaces.usuario.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsuarioRole {
    ADMIN("admin"),
    USER("user");

    private final String role;
}
