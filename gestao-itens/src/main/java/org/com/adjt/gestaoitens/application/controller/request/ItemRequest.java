package org.com.adjt.gestaoitens.application.controller.request;

public record ItemRequest(
        String nome,
        String descricao,
        String categoria,
        Double preco,
        String urlImagem,
        Integer quantidade
){}