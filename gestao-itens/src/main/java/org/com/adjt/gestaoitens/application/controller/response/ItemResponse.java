package org.com.adjt.gestaoitens.application.controller.response;

public record ItemResponse (
        String id,
        String nome,
        String descricao,
        String categoria,
        Double preco,
        String urlImagem,
        Integer quantidade
){}