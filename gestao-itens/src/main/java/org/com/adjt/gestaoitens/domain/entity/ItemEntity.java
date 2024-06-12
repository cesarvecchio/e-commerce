package org.com.adjt.gestaoitens.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class ItemEntity {
    @Id
    private String id;
    private String nome;
    private String descricao;
    private String categoria;
    private Double preco;
    private String urlImagem;
    private Integer quantidade;

    public ItemEntity(String nome, String descricao, String categoria, Double preco, String urlImagem, Integer quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.urlImagem = urlImagem;
        this.quantidade = quantidade;
    }
}
