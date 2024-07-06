package br.com.fiap.mspagamento.interfaces.carrinho.response;

import br.com.fiap.mspagamento.interfaces.carrinho.valueobject.ItemCarrinhoResponse;

import java.util.List;
import java.util.UUID;

public record CarrinhoResponse(
        UUID id,
        String usuarioId,
        List<ItemCarrinhoResponse> itens
) {
}