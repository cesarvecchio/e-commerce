package org.com.adjt.gestaoitens.domain.service;

import org.com.adjt.gestaoitens.application.controller.request.ItemRequest;
import org.com.adjt.gestaoitens.application.controller.response.ItemResponse;
import org.com.adjt.gestaoitens.domain.entity.ItemEntity;
import org.com.adjt.gestaoitens.exceptions.NaoEncontradoException;
import org.com.adjt.gestaoitens.infrastructure.repository.ItemRepository;
import org.com.adjt.gestaoitens.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public ItemResponse criar(ItemRequest itemRequest){
        ItemEntity itemEntity = toEntity(itemRequest);

        return toResponse(itemRepository.save(itemEntity));
    }

    public ItemResponse buscarPorId(String id){
        return toResponse(existePorId(id));
    }

    public List<ItemResponse> buscarTodos(){
        return itemRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ItemResponse atualizar(String id, ItemRequest itemRequest) {
         ItemEntity itemEntity = existePorId(id);

         Utils.copyNonNullProperties(itemRequest, itemEntity);

         return toResponse(itemRepository.save(itemEntity));
    }

    public void deletarPorId(String id){
        itemRepository.delete(existePorId(id));
    }


    private ItemEntity existePorId(String id) {

        return itemRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException(
                    String.format("Item com o id '%s' nao encontrado", id)));

    }

    private ItemResponse toResponse(ItemEntity itemEntity){
        return new ItemResponse(
                itemEntity.getId(),
                itemEntity.getNome(),
                itemEntity.getDescricao(),
                itemEntity.getCategoria(),
                itemEntity.getPreco(),
                itemEntity.getUrlImagem(),
                itemEntity.getQuantidade()
        );
    }

    private ItemEntity toEntity(ItemRequest itemRequest) {
        return new ItemEntity(
                itemRequest.nome(),
                itemRequest.descricao(),
                itemRequest.categoria(),
                itemRequest.preco(),
                itemRequest.urlImagem(),
                itemRequest.quantidade()
        );
    }
}
