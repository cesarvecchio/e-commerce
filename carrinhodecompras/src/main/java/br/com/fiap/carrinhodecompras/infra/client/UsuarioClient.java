package br.com.fiap.carrinhodecompras.infra.client;

import br.com.fiap.carrinhodecompras.infra.client.dto.UsuarioDTO;
import br.com.fiap.carrinhodecompras.infra.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-usuario", path = "/user", configuration = FeignConfig.class)
public interface UsuarioClient {

    @GetMapping("/{login}")
    UsuarioDTO findByLogin(@RequestHeader("Authorization") String token , @PathVariable String login);
}
