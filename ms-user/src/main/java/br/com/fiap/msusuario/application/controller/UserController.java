package br.com.fiap.msusuario.application.controller;

import br.com.fiap.msusuario.application.controller.request.LoginRequest;
import br.com.fiap.msusuario.application.controller.request.UserRequest;
import br.com.fiap.msusuario.application.controller.response.LoginResponse;
import br.com.fiap.msusuario.application.controller.response.UserRegisterResponse;
import br.com.fiap.msusuario.application.controller.response.UserResponse;
import br.com.fiap.msusuario.application.documentation.UserControllerDoc;
import br.com.fiap.msusuario.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController implements UserControllerDoc {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.registerUser(userRequest));
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserResponse> findUserByLogin(@PathVariable String login) {
        return ResponseEntity.ok(userService.findUserByLogin(login));
    }
}
