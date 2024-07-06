package br.com.fiap.mspagamento.application.controller;

import br.com.fiap.mspagamento.application.controller.request.PagamentoRequestDTO;
import br.com.fiap.mspagamento.domain.service.PagamentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping
    public Mono<String> healthCheck() {
        return Mono.just("OK");
    }

    @PostMapping("/pagar")
    public Mono<ResponseEntity<String>> realizarPagamento(@RequestHeader("Authorization") String token,
                                          @RequestBody PagamentoRequestDTO pagamentoRequestDTO) throws JsonProcessingException {
        String tokenPreparado = token.replace("Bearer ", "");
        return pagamentoService.realizarPagamento(tokenPreparado, pagamentoRequestDTO.formaPagamento())
                .map(ResponseEntity::ok);
    }
}
