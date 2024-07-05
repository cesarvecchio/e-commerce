package br.com.fiap.carrinhodecompras.domain.entity;

import br.com.fiap.carrinhodecompras.domain.enums.PagamentoEnum;
import br.com.fiap.carrinhodecompras.domain.enums.StatusEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "carrinhos")
@Data
public class Carrinho {

    @Id
    private UUID id;
    private String usuarioId;
    private List<ItemCarrinho> itens;
    private StatusEnum status;
    private PagamentoEnum formaPagamento;

    public Carrinho() {
        this.id = UUID.randomUUID(); // Inicializa o UUID automaticamente
    }
}

