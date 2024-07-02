package org.com.adjt.gestaoitens.infrastructure.client;

import org.com.adjt.gestaoitens.infrastructure.client.dto.UsuarioDTO;
import org.com.adjt.gestaoitens.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-user", path = "/user", configuration = FeignConfig.class)
public interface UsuarioClient {

//    @GetMapping("/login")
//    UsuarioResponse login(@RequestBody UsuarioRequest usuarioRequest);

    @GetMapping("/{login}")
    UsuarioDTO findByLogin(@RequestHeader("Authorization") String token , @PathVariable String login);
}
