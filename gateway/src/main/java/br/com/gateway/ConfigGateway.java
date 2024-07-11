package br.com.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigGateway {
    @Value("${ms-cliente-url}")
    String msClienteUrl;
    @Value("${ms-produtos-url}")
    String msProdutosUrl;
    @Value("${ms-carrinho-compras-url}")
    String msCarrinhoComprasUrl;
    @Value("${ms-pagamento-url}")
    String msPagamentoUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user", r -> r.path("/user/**")
                        .uri(msClienteUrl))
                .route("gestao-itens", r -> r.path("/itens/**")
                        .uri(msProdutosUrl))
                .route("carrinho", r -> r.path("/carrinho/**")
                        .uri(msCarrinhoComprasUrl))
                .route("pagamento", r -> r.path("/pagamentos/**")
                        .uri(msPagamentoUrl))
                .build();
    }

}
