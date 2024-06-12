package org.com.adjt.gestaoitens.infrastructure.client.mock;

import org.com.adjt.gestaoitens.infrastructure.client.UsuarioClient;
import org.com.adjt.gestaoitens.infrastructure.client.dto.UsuarioDTO;
import org.com.adjt.gestaoitens.utils.enums.UsuarioRole;
import org.springframework.stereotype.Component;

@Component
public class UsuarioClientMock implements UsuarioClient {

    @Override
    public UsuarioDTO findByLogin(String login) {
        if(login.equals("ADMIN")) {
            return new UsuarioDTO(
                    "1",
                    login,
                    "$2y$10$aFoZefH/3.Ju3pF9RP5/UuDFXkpYZdJ2b3Sjps0IrreUMjYhrEKnu",
                    UsuarioRole.ADMIN
            );
        }

        return new UsuarioDTO(
                "2",
                login,
                "USER",
                UsuarioRole.USER
        );
    }
}
