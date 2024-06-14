package org.com.adjt.gestaoitens.application.controller;

import org.com.adjt.gestaoitens.application.controller.request.ItemRequest;
import org.com.adjt.gestaoitens.application.controller.response.ItemResponse;
import org.com.adjt.gestaoitens.domain.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> criar(@RequestBody ItemRequest itemRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.criar(itemRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> buscarPorId(@PathVariable String id){
        return ResponseEntity.ok(itemService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> buscarTodos(){
        return ResponseEntity.ok(itemService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> atualizar(@PathVariable String id, @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.atualizar(id, itemRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable String id) {
        itemService.deletarPorId(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/baixa-estoque/{id}/{quantidade}")
    public ResponseEntity<ItemResponse> baixaEstoque(@PathVariable String id, @PathVariable Integer quantidade) {
        return ResponseEntity.ok(itemService.baixaEstoque(id, quantidade));
    }

    @PutMapping("/entrada-estoque/{id}/{quantidade}")
    public ResponseEntity<ItemResponse> entradaEstoque(@PathVariable String id, @PathVariable Integer quantidade) {
        return ResponseEntity.ok(itemService.entradaEstoque(id, quantidade));
    }
}
