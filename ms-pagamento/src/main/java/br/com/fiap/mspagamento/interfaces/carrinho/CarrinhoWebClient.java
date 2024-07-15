package br.com.fiap.mspagamento.interfaces.carrinho;

import br.com.fiap.mspagamento.interfaces.carrinho.request.AtualizarCarrinhoDTO;
import br.com.fiap.mspagamento.interfaces.carrinho.response.CarrinhoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CarrinhoWebClient {

    @Value("${carrinho.url}")
    private String carrinhoUrl;

    private final WebClient.Builder webClientBuilder;

    public CarrinhoWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<CarrinhoResponse> obterCarrinho(String token) {
        return webClientBuilder.build()
                .get()
                .uri(carrinhoUrl + "/carrinho")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(CarrinhoResponse.class);
    }

    public Mono<CarrinhoResponse> atualizarCarrinho(String token, AtualizarCarrinhoDTO atualizarCarrinhoDTO) {
        return webClientBuilder.build()
                .put()
                .uri(carrinhoUrl + "/carrinho/atualizar")
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(Mono.just(atualizarCarrinhoDTO), AtualizarCarrinhoDTO.class)
                .retrieve()
                .bodyToMono(CarrinhoResponse.class);
    }
}
