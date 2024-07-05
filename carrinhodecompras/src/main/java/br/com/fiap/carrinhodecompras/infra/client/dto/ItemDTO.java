package br.com.fiap.carrinhodecompras.infra.client.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Getter
@Setter
public class ItemDTO {
    private String id;
    private String nome;
    private String descricao;
    private String categoria;
    private Double preco;
    private String urlImagem;
    private Integer quantidade;

    public ItemDTO(String id, String nome, String descricao, String categoria, Double preco, String urlImagem, Integer quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.urlImagem = urlImagem;
        this.quantidade = quantidade;
    }
}
