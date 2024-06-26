package org.com.adjt.gestaoitens.domain.service;

import org.com.adjt.gestaoitens.application.controller.request.ItemRequest;
import org.com.adjt.gestaoitens.application.controller.response.ItemResponse;
import org.com.adjt.gestaoitens.domain.entity.ItemEntity;
import org.com.adjt.gestaoitens.exceptions.NaoEncontradoException;
import org.com.adjt.gestaoitens.exceptions.QuantidadeEstoqueException;
import org.com.adjt.gestaoitens.infrastructure.repository.ItemRepository;
import org.com.adjt.gestaoitens.utils.BuildItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    private ItemService itemService;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        itemService = new ItemService(itemRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Nested
    class Criar {
        @Test
        void deveCriarItem(){
            var itemRequest = BuildItem.itemRequest();
            var itemEntity = BuildItem.itemEntity();

            var itemEntityResposta = BuildItem.itemEntity();
            itemEntityResposta.setId(BuildItem.id());

            var itemResponse = BuildItem.itemResponse();

            when(itemRepository.save(itemEntity)).thenReturn(itemEntityResposta);

            var resultado = itemService.criar(itemRequest);

            assertEquals(itemResponse, resultado);

            verify(itemRepository).save(itemEntity);
        }
    }

    @Nested
    class BuscarPorId {
        @Test
        void deveBuscarItemPorId() {
            var id = BuildItem.id();
            var itemEntity = BuildItem.itemEntity();
            itemEntity.setId(id);

            var itemResponse = BuildItem.itemResponse();

            when(itemRepository.findById(id)).thenReturn(Optional.of(itemEntity));

            var resultado = itemService.buscarPorId(id);

            assertEquals(itemResponse, resultado);

            verify(itemRepository).findById(id);
        }

        @Test
        void deveGerarExcecao_NaoEncontrado_QuandoBuscarItemPorId() {
            var id = BuildItem.id();

            naoEncontrado(id);

            assertThatThrownBy(() -> itemService.buscarPorId(id))
                    .isInstanceOf(NaoEncontradoException.class)
                    .hasMessage(String.format("Item com o id '%s' nao encontrado", id));

            verify(itemRepository).findById(id);
        }
    }

    @Nested
    class BuscarTodos {
        @Test
        void deveBuscarTodosItens() {
            var itemEntity = BuildItem.itemEntity();
            itemEntity.setId(BuildItem.id());
            var itemEntityList = List.of(itemEntity);
            var itemResponseList = List.of(BuildItem.itemResponse());

            when(itemRepository.findAll()).thenReturn(itemEntityList);

            var resultado = itemService.buscarTodos();

            assertEquals(itemResponseList, resultado);

            verify(itemRepository).findAll();
        }
    }

    @Nested
    class Atualizar {
        @Test
        void deveAtualizarItem() {
            var id = BuildItem.id();

            var itemRequest = new ItemRequest(
                    "NomeAtualizado",
                    null, null, null, null, null
            );

            var itemEntity = BuildItem.itemEntity();
            itemEntity.setId(id);

            var itemEntityAtualizado = BuildItem.itemEntity();
            itemEntityAtualizado.setId(id);
            itemEntityAtualizado.setNome(itemRequest.nome());

            var itemResponse = new ItemResponse(
                    id,
                    itemRequest.nome(),
                    "Descricao",
                    "Categoria",
                    10.00,
                    "UrlImagem",
                    10
            );

            when(itemRepository.findById(id)).thenReturn(Optional.of(itemEntity));
            when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntityAtualizado);

            var resultado = itemService.atualizar(id, itemRequest);

            assertEquals(itemResponse, resultado);

            verify(itemRepository).findById(id);
            verify(itemRepository).save(any(ItemEntity.class));
        }

        @Test
        void deveGerarExcecao_NaoEncontrado_QuandoAtualizaItem() {
            var id = BuildItem.id();

            var itemRequest = new ItemRequest(
                    "NomeAtualizado",
                    null, null, null, null, null
            );

            naoEncontrado(id);

            assertThatThrownBy(() -> itemService.atualizar(id, itemRequest))
                    .isInstanceOf(NaoEncontradoException.class)
                    .hasMessage(String.format("Item com o id '%s' nao encontrado", id));

            verify(itemRepository).findById(id);
        }
    }

    @Nested
    class DeletarPorId {
        @Test
        void deveDeletarPorId() {
            var id = BuildItem.id();
            var itemEntity = BuildItem.itemEntity();
            itemEntity.setId(id);

            when(itemRepository.findById(id)).thenReturn(Optional.of(itemEntity));

            itemService.deletarPorId(id);

            verify(itemRepository).findById(id);
            verify(itemRepository).delete(itemEntity);
        }

        @Test
        void deveGerarExcecao_NaoEncontrado_QuandoDeletarPorId() {
            var id = BuildItem.id();

            naoEncontrado(id);

            assertThatThrownBy(() -> itemService.deletarPorId(id))
                    .isInstanceOf(NaoEncontradoException.class)
                    .hasMessage(String.format("Item com o id '%s' nao encontrado", id));

            verify(itemRepository).findById(id);
        }
    }

    @Nested
    class BaixaEstoque {
        @Test
        void deveDarBaixaNoEstoque() {
            var id = BuildItem.id();
            var qtd = 1;
            var itemEntity = BuildItem.itemEntity();
            itemEntity.setId(id);
            var itemResponse = new ItemResponse(
                    id,
                    "Nome",
                    "Descricao",
                    "Categoria",
                    10.00,
                    "UrlImagem",
                    9
            );

            when(itemRepository.findById(id)).thenReturn(Optional.of(itemEntity));
            when(itemRepository.save(itemEntity)).thenReturn(itemEntity);

            var resultado = itemService.baixaEstoque(id, qtd);

            assertEquals(itemResponse, resultado);

            verify(itemRepository).findById(id);
            verify(itemRepository).save(itemEntity);
        }

        @Test
        void deveGerarExcecao_NaoEncontrado_QuandoDarBaixaNoEstoque() {
            var id = BuildItem.id();
            var qtd = 1;

            naoEncontrado(id);

            assertThatThrownBy(() -> itemService.baixaEstoque(id, qtd))
                    .isInstanceOf(NaoEncontradoException.class)
                    .hasMessage(String.format("Item com o id '%s' nao encontrado", id));


            verify(itemRepository).findById(id);
        }

        @Test
        void deveGerarExcecao_QuantidadeEstoque_QuandoDarBaixaNoEstoque() {
            var id = BuildItem.id();
            var qtd = 11;
            var itemEntity = BuildItem.itemEntity();
            itemEntity.setId(id);

            when(itemRepository.findById(id)).thenReturn(Optional.of(itemEntity));

            assertThatThrownBy(() -> itemService.baixaEstoque(id, qtd))
                    .isInstanceOf(QuantidadeEstoqueException.class)
                    .hasMessage(String.format("Não é possivel dar baixa no estoque do item de id:[%s] %n " +
                                    "Quantidade atual:[%s] %n " +
                                    "Quantidade para dar baixa:[%s]",
                            id, itemEntity.getQuantidade(), qtd));

            verify(itemRepository).findById(id);
        }
    }

    @Nested
    class EntradaEstoque {
        @Test
        void deveDarEntradaNoEstoque() {
            var id = BuildItem.id();
            var qtd = 1;
            var itemEntity = BuildItem.itemEntity();
            itemEntity.setId(id);
            var itemResponse = new ItemResponse(
                    id,
                    "Nome",
                    "Descricao",
                    "Categoria",
                    10.00,
                    "UrlImagem",
                    11
            );

            when(itemRepository.findById(id)).thenReturn(Optional.of(itemEntity));
            when(itemRepository.save(itemEntity)).thenReturn(itemEntity);

            var resultado = itemService.entradaEstoque(id, qtd);

            assertEquals(itemResponse, resultado);

            verify(itemRepository).findById(id);
            verify(itemRepository).save(itemEntity);
        }

        @Test
        void deveGerarExcecao_NaoEncontrado_QuandoDarEntradaNoEstoque() {
            var id = BuildItem.id();
            var qtd = 1;

            naoEncontrado(id);

            assertThatThrownBy(() -> itemService.entradaEstoque(id, qtd))
                    .isInstanceOf(NaoEncontradoException.class)
                    .hasMessage(String.format("Item com o id '%s' nao encontrado", id));


            verify(itemRepository).findById(id);
        }
    }

    private void naoEncontrado(String id){
        when(itemRepository.findById(id)).thenReturn(Optional.empty());
    }

}
