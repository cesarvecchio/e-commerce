package br.com.fiap.carrinhodecompras.application.controller.request;

import br.com.fiap.carrinhodecompras.domain.enums.PagamentoEnum;
import br.com.fiap.carrinhodecompras.domain.enums.StatusEnum;

public record AtualizarCarrinhoDTO(
        StatusEnum status,
        PagamentoEnum formaPagamento
) {
}
