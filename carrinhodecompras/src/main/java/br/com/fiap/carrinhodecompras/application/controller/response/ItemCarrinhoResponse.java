package br.com.fiap.carrinhodecompras.application.controller.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCarrinhoResponse(
        String id,
        Integer quantidade,
        Double preco,
        String nome
) {
}
