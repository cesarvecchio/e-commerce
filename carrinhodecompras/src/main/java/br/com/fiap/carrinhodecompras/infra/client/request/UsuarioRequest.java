package br.com.fiap.carrinhodecompras.infra.client.request;

public record UsuarioRequest(
        String login,
        String senha
) {}
