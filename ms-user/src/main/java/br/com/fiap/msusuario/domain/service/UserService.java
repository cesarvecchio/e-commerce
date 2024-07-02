package br.com.fiap.msusuario.domain.service;

import br.com.fiap.msusuario.application.controller.request.LoginRequest;
import br.com.fiap.msusuario.application.controller.request.UserRequest;
import br.com.fiap.msusuario.application.controller.response.LoginResponse;
import br.com.fiap.msusuario.application.controller.response.UserRegisterResponse;
import br.com.fiap.msusuario.application.controller.response.UserResponse;

public interface UserService {
    UserRegisterResponse registerUser(UserRequest userRequest);

    LoginResponse authenticateUser(LoginRequest loginRequest);

    UserResponse findUserByLogin(String login);
}
