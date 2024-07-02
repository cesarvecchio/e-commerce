package br.com.fiap.msusuario.domain.security.authentication;

import br.com.fiap.msusuario.domain.entity.User;
import br.com.fiap.msusuario.domain.security.userdetails.UserDetailsImpl;
import br.com.fiap.msusuario.exceptions.UserNotFoundException;
import br.com.fiap.msusuario.infrastructure.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = recoverToken(request);

        if (token.isPresent()) {
            String login = tokenService.validateToken(token.get());
            User user = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User not found"));
            UserDetails userDetails = new UserDetailsImpl(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> recoverToken(HttpServletRequest request) {
        Optional<String> authHeader = ofNullable(request.getHeader("Authorization"));
        if (authHeader.isPresent()) {
            return authHeader.map(a -> a.replace("Bearer ", ""));
        }
        return empty();
    }
}

