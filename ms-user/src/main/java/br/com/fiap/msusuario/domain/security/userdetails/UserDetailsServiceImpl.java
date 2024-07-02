package br.com.fiap.msusuario.domain.security.userdetails;

import br.com.fiap.msusuario.domain.entity.User;
import br.com.fiap.msusuario.exceptions.BadCredentialsException;
import br.com.fiap.msusuario.infrastructure.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByLogin(username).orElseThrow(() -> new BadCredentialsException("User not found"));
        return new UserDetailsImpl(user);
    }
}
