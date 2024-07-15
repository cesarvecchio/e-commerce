package org.com.adjt.gestaoitens.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsuarioRole {
    ADMIN("admin"),
    USER("user");

    private final String role;
}
