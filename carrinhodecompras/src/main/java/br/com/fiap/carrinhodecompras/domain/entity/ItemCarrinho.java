package br.com.fiap.carrinhodecompras.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Getter
@Setter
public class ItemCarrinho {
    private String itemId;
    private Integer quantidade;
    private Double preco;
    private String nome;
}
