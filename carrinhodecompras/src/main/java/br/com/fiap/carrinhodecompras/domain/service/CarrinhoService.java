package br.com.fiap.carrinhodecompras.domain.service;

import br.com.fiap.carrinhodecompras.application.controller.request.AtualizarCarrinhoDTO;
import br.com.fiap.carrinhodecompras.application.controller.response.CarrinhoResponse;
import br.com.fiap.carrinhodecompras.application.controller.response.ItemCarrinhoResponse;
import br.com.fiap.carrinhodecompras.domain.entity.Carrinho;
import br.com.fiap.carrinhodecompras.domain.entity.ItemCarrinho;
import br.com.fiap.carrinhodecompras.domain.enums.StatusEnum;
import br.com.fiap.carrinhodecompras.exceptions.NaoEncontradoException;
import br.com.fiap.carrinhodecompras.infra.client.ItemServiceClient;
import br.com.fiap.carrinhodecompras.infra.client.dto.ItemDTO;
import br.com.fiap.carrinhodecompras.infra.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarrinhoService {
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ItemServiceClient itemServiceClient;

    public CarrinhoResponse obterCarrinhoPorUsuarioId(String usuarioId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioIdAndStatus(usuarioId, StatusEnum.AGUARDANDO_PAGAMENTO);
        if (carrinho == null) {
            throw new NaoEncontradoException(
                    String.format("Carrinho Vazio para o usuário '%s'", usuarioId));
        }
        return toCarrinhoResponse(carrinho);
    }

    public CarrinhoResponse adicionarItemAoCarrinho(String usuarioId, String itemId, int quantidade) {
        // Verificar se o item existe no serviço de itens
        ItemDTO item = itemServiceClient.baixaEstoque(itemId, quantidade);
        if (item == null) {
            throw new NaoEncontradoException(
                    String.format("Item com o id '%s' nao encontrado", itemId));
        }

        Carrinho carrinho = obterOuCriarCarrinho(usuarioId);

        // Inicializar a lista de itens se ela estiver nula
        if (carrinho.getItens() == null) {
            carrinho.setItens(new ArrayList<>());
        }

        Optional<ItemCarrinho> itemExistente = carrinho.getItens()
                .stream()
                .filter(itemCarrinho -> itemCarrinho.getItemId().equals(itemId))
                .findFirst();

        if (itemExistente.isPresent()) {
            itemExistente.get().setQuantidade(itemExistente.get().getQuantidade() + quantidade);
        } else {
            ItemCarrinho novoItem = new ItemCarrinho();
            novoItem.setItemId(itemId);
            novoItem.setNome(item.getNome());
            novoItem.setPreco(item.getPreco());
            novoItem.setQuantidade(quantidade);
            carrinho.getItens().add(novoItem);
        }

        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);
        return toCarrinhoResponse(carrinhoSalvo);
    }

    public CarrinhoResponse removerItemDoCarrinho(String usuarioId, String itemId, int quantidade) {
        Carrinho carrinho = obterOuCriarCarrinho(usuarioId);

        // Inicializar a lista de itens se ela estiver nula
        if (carrinho.getItens() == null) {
            throw new NaoEncontradoException(
                    String.format("Carrinho Vazio para o usuário '%s'", usuarioId));
        }

        Optional<ItemCarrinho> itemExistente = carrinho
                .getItens()
                .stream()
                .filter(itemCarrinho -> itemCarrinho
                        .getItemId()
                        .equals(itemId))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrinho item = itemExistente.get();
            item.setQuantidade(item.getQuantidade() - quantidade);
            itemServiceClient.entradaEstoque(itemId, quantidade);
            if (item.getQuantidade() <= 0) {
                carrinho.getItens().remove(item);
            }
        } else {

            throw new NaoEncontradoException(
                    String.format("Item com o id '%s' não existe no carrinho", itemId));
        }

        carrinho = carrinhoRepository.save(carrinho);
        return toCarrinhoResponse(carrinho);
    }

    public CarrinhoResponse atualizarCarrinho(String usuarioId, AtualizarCarrinhoDTO atualizarCarrinhoDTO) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioIdAndStatus(usuarioId, StatusEnum.AGUARDANDO_PAGAMENTO);
        if (carrinho == null) {
            throw new NaoEncontradoException(
                    String.format("Carrinho Vazio para o usuário '%s'", usuarioId));
        }
        carrinho.setStatus(atualizarCarrinhoDTO.status());
        carrinho.setFormaPagamento(atualizarCarrinhoDTO.formaPagamento());
        return toCarrinhoResponse(carrinhoRepository.save(carrinho));
    }

    private Carrinho obterOuCriarCarrinho(String usuarioId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioIdAndStatus(usuarioId, StatusEnum.AGUARDANDO_PAGAMENTO);
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setUsuarioId(usuarioId);
            carrinho.setStatus(StatusEnum.AGUARDANDO_PAGAMENTO);
            carrinhoRepository.save(carrinho);
        }
        return carrinho;
    }

    private CarrinhoResponse toCarrinhoResponse(Carrinho carrinho) {
        return new CarrinhoResponse(
                carrinho.getId(),
                carrinho.getUsuarioId(),
                carrinho.getItens().stream().map(itemCarrinho -> new ItemCarrinhoResponse(
                        itemCarrinho.getItemId(),
                        itemCarrinho.getQuantidade(),
                        itemCarrinho.getPreco(),
                        itemCarrinho.getNome()
                )).toList()
        );
    }
}

