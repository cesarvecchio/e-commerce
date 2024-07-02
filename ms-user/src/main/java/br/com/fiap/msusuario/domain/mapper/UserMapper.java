package br.com.fiap.msusuario.domain.mapper;

import br.com.fiap.msusuario.application.controller.response.UserRegisterResponse;
import br.com.fiap.msusuario.application.controller.response.UserResponse;
import br.com.fiap.msusuario.domain.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserRegisterResponse userToUserRegisterResponse(User user) {
        return new UserRegisterResponse(user.getLogin(), user.getPassword());
    }

    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
