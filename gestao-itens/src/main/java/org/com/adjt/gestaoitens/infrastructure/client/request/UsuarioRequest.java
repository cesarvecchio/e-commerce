package org.com.adjt.gestaoitens.infrastructure.client.request;

public record UsuarioRequest(
        String login,
        String senha
) {}
