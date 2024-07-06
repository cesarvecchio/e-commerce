package br.com.fiap.mspagamento.domain.service;

import br.com.fiap.mspagamento.application.controller.exceptions.NaoEncontradoException;
import br.com.fiap.mspagamento.domain.enums.PagamentoEnum;
import br.com.fiap.mspagamento.domain.enums.StatusEnum;
import br.com.fiap.mspagamento.interfaces.carrinho.CarrinhoWebClient;
import br.com.fiap.mspagamento.interfaces.carrinho.request.AtualizarCarrinhoDTO;
import br.com.fiap.mspagamento.utils.Utils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PagamentoService {

    private final CarrinhoWebClient carrinhoWebClient;
    private final Utils utils;

    public PagamentoService(CarrinhoWebClient carrinhoWebClient, Utils utils) {
        this.carrinhoWebClient = carrinhoWebClient;
        this.utils = utils;
    }

    public Mono<String> realizarPagamento(String token, PagamentoEnum formaPagamento) {
        return carrinhoWebClient.obterCarrinho(token)
                .flatMap(carrinho -> {
                    if (carrinho != null) {
                        AtualizarCarrinhoDTO atualizarCarrinhoDTO = new AtualizarCarrinhoDTO(StatusEnum.PAGO, formaPagamento);
                        return carrinhoWebClient.atualizarCarrinho(token, atualizarCarrinhoDTO)
                                .flatMap(updatedCarrinho -> Mono.just("Pagamento realizado com sucesso!")
                                );
                    } else {
                        return Mono.error(new NaoEncontradoException("Carrinho não encontrado"));
                    }
                });
    }
}
