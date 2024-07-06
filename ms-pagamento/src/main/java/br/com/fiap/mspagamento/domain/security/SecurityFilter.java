package br.com.fiap.mspagamento.domain.security;

import br.com.fiap.mspagamento.interfaces.usuario.UsuarioWebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class SecurityFilter implements WebFilter {
    private final TokenService tokenService;
    private final UsuarioWebClient usuarioWebClient;

    public SecurityFilter(TokenService tokenService, UsuarioWebClient usuarioWebClient) {
        this.tokenService = tokenService;
        this.usuarioWebClient = usuarioWebClient;
    }

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        String token = recoverToken(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        if (token != null) {
            String login = tokenService.validateToken(token);
            return usuarioWebClient.findByLogin(token, login)
                    .flatMap(usuarioDTO -> {
                        var authentication = new UsernamePasswordAuthenticationToken(usuarioDTO, null, usuarioDTO.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        return chain.filter(exchange);
                    });
        }
        return chain.filter(exchange);
    }

    private String recoverToken(String authHeader) {
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
