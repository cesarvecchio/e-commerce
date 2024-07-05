package br.com.fiap.carrinhodecompras.application.controller;

import br.com.fiap.carrinhodecompras.application.controller.request.AtualizarCarrinhoDTO;
import br.com.fiap.carrinhodecompras.application.controller.response.CarrinhoResponse;
import br.com.fiap.carrinhodecompras.domain.security.TokenService;
import br.com.fiap.carrinhodecompras.domain.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public CarrinhoResponse obterCarrinho(@RequestHeader("Authorization") String token) {
        //UUID usuarioId = UUID.fromString(token.getSubject());
        var tokenReplace = token.replace("Bearer ", "");
        String id = tokenService.validateToken(tokenReplace);
        return carrinhoService.obterCarrinhoPorUsuarioId(id);
    }

    @PostMapping("/adicionar")
    public CarrinhoResponse adicionarItemAoCarrinho(@RequestHeader("Authorization") String token,
                                            @RequestParam String itemId,
                                            @RequestParam int quantidade) {
        var tokenReplace = token.replace("Bearer ", "");
        String id = tokenService.validateToken(tokenReplace);
        return carrinhoService.adicionarItemAoCarrinho(id, itemId, quantidade);
    }

    @PostMapping("/remover")
    public CarrinhoResponse removerItemDoCarrinho(@RequestHeader("Authorization") String token,
                                          @RequestParam String itemId,
                                          @RequestParam int quantidade) {
        var tokenReplace = token.replace("Bearer ", "");
        String id = tokenService.validateToken(tokenReplace);
        return carrinhoService.removerItemDoCarrinho(id, itemId, quantidade);
    }

    @PutMapping("/atualizar")
    public CarrinhoResponse atualizarCarrinho(@RequestHeader("Authorization") String token,
                                              @RequestBody AtualizarCarrinhoDTO atualizarCarrinhoDTO) {
        var tokenReplace = token.replace("Bearer ", "");
        String id = tokenService.validateToken(tokenReplace);
        return carrinhoService.atualizarCarrinho(id, atualizarCarrinhoDTO);
    }
}