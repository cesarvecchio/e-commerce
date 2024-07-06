package br.com.fiap.mspagamento.interfaces.carrinho.request;

import br.com.fiap.mspagamento.domain.enums.PagamentoEnum;
import br.com.fiap.mspagamento.domain.enums.StatusEnum;

public record AtualizarCarrinhoDTO(
        StatusEnum status,
        PagamentoEnum formaPagamento
) {
}