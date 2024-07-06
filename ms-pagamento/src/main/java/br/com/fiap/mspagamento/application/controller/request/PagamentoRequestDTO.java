package br.com.fiap.mspagamento.application.controller.request;

import br.com.fiap.mspagamento.domain.enums.PagamentoEnum;

public record PagamentoRequestDTO(
        PagamentoEnum formaPagamento
) {
}
