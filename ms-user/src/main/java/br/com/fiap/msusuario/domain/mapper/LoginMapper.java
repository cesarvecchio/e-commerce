package br.com.fiap.msusuario.domain.mapper;

import br.com.fiap.msusuario.application.controller.response.LoginResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoginMapper {

    public LoginResponse stringToLoginResponse(String token) {
        return new LoginResponse(token);
    }
}
