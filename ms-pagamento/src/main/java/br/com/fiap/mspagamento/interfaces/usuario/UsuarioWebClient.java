package br.com.fiap.mspagamento.interfaces.usuario;


import br.com.fiap.mspagamento.interfaces.usuario.response.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UsuarioWebClient {

    @Value("${usuario.url}")
    private String usuarioUrl;

    private final WebClient.Builder webClientBuilder;

    public UsuarioWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<UsuarioDTO> findByLogin(String token, String login) {
        return webClientBuilder.build()
                .get()
                .uri(usuarioUrl + "/user/{login}", login)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(UsuarioDTO.class);
    }

}
