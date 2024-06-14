//package org.com.adjt.gestaoitens.domain.service;
//
//import org.com.adjt.gestaoitens.infrastructure.client.UsuarioClient;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthorizationService implements UserDetailsService {
//
//    private final UsuarioClient repository;
//
//    public AuthorizationService(@Qualifier("usuarioClientMock")UsuarioClient repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return repository.findByLogin(username);
//    }
//}
