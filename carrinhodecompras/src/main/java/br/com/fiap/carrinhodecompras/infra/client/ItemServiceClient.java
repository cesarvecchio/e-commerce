package br.com.fiap.carrinhodecompras.infra.client;

import br.com.fiap.carrinhodecompras.infra.client.dto.ItemDTO;
import br.com.fiap.carrinhodecompras.infra.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "ms-gestao-itens", configuration = FeignConfig.class)
public interface ItemServiceClient {
    @GetMapping("/itens/{id}")
    ItemDTO buscarPorId(@PathVariable String id);

    @PutMapping("/itens/baixa-estoque/{id}/{quantidade}")
    ItemDTO  baixaEstoque(@PathVariable String id, @PathVariable Integer quantidade);

    @PutMapping("/itens/entrada-estoque/{id}/{quantidade}")
    ItemDTO  entradaEstoque(@PathVariable String id, @PathVariable Integer quantidade);
}
