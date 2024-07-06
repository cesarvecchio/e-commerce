package br.com.fiap.mspagamento.interfaces.carrinho.valueobject;

public record ItemCarrinhoResponse(
        String id,
        Integer quantidade,
        Double preco,
        String nome
) {
}
