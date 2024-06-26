package org.com.adjt.gestaoitens.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.com.adjt.gestaoitens.exceptions.QuantidadeEstoqueException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
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

    public void baixaEstoque(Integer quantidade) {
        if(getQuantidade() < quantidade){
            throw new QuantidadeEstoqueException(
                    String.format("Não é possivel dar baixa no estoque do item de id:[%s] %n " +
                                    "Quantidade atual:[%s] %n " +
                                    "Quantidade para dar baixa:[%s]",
                            id, getQuantidade(), quantidade));
        }
        this.quantidade -= quantidade;
    }

    public void entradaEstoque(Integer quantidade) {
        this.quantidade += quantidade;
    }
}
