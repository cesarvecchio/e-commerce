package org.com.adjt.gestaoitens.infrastructure.client;

import org.com.adjt.gestaoitens.infrastructure.client.dto.UsuarioDTO;
import org.com.adjt.gestaoitens.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-usuario", path = "/usuario", configuration = FeignConfig.class)
public interface UsuarioClient {

//    @GetMapping("/login")
//    UsuarioResponse login(@RequestBody UsuarioRequest usuarioRequest);

    @GetMapping("/login")
    UsuarioDTO findByLogin(@RequestBody String login);
}
