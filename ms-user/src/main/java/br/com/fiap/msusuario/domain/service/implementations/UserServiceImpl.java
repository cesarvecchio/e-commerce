package br.com.fiap.msusuario.domain.service.implementations;

import br.com.fiap.msusuario.application.controller.request.LoginRequest;
import br.com.fiap.msusuario.application.controller.request.UserRequest;
import br.com.fiap.msusuario.application.controller.response.LoginResponse;
import br.com.fiap.msusuario.application.controller.response.UserRegisterResponse;
import br.com.fiap.msusuario.application.controller.response.UserResponse;
import br.com.fiap.msusuario.domain.entity.User;
import br.com.fiap.msusuario.domain.entity.enums.UserRole;
import br.com.fiap.msusuario.domain.mapper.LoginMapper;
import br.com.fiap.msusuario.domain.mapper.UserMapper;
import br.com.fiap.msusuario.domain.security.authentication.TokenService;
import br.com.fiap.msusuario.domain.security.userdetails.UserDetailsImpl;
import br.com.fiap.msusuario.domain.service.UserService;
import br.com.fiap.msusuario.exceptions.BadCredentialsException;
import br.com.fiap.msusuario.exceptions.UserAlreadyExistsException;
import br.com.fiap.msusuario.exceptions.UserNotFoundException;
import br.com.fiap.msusuario.infrastructure.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository repository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @Override
    public UserRegisterResponse registerUser(UserRequest userRequest) {
        repository.findByLogin(userRequest.login()).ifPresent(user -> {
            throw new UserAlreadyExistsException("User with this login already exists");
        });

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRequest.password());
        User newUser = User.builder()
                .login(userRequest.login())
                .password(encryptedPassword)
                .role(ofNullable(userRequest.role()).orElse(UserRole.USER))
                .build();

        return UserMapper.userToUserRegisterResponse(repository.save(newUser));
    }

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password());
        try {
            Authentication auth = authenticationManager.authenticate(usernamePassword);
            return LoginMapper.stringToLoginResponse(tokenService.generateToken((UserDetailsImpl) auth.getPrincipal()));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Wrong username or password");
        }
    }

    @Override
    public UserResponse findUserByLogin(String login) {
        User user = repository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.userToUserResponse(user);
    }
}
