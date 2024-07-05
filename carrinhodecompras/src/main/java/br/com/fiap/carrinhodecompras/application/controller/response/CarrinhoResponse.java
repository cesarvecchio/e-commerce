package br.com.fiap.carrinhodecompras.application.controller.response;

import java.util.List;
import java.util.UUID;

public record CarrinhoResponse(
    UUID id,
    String usuarioId,
    List<ItemCarrinhoResponse> itens) {
}
