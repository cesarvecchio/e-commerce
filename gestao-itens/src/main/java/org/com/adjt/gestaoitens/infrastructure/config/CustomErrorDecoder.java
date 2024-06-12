package org.com.adjt.gestaoitens.infrastructure.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.com.adjt.gestaoitens.exceptions.NaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            if(methodKey.contains("Usuario")){

                return new NaoEncontradoException("Usuario nao encontrado");
            }

            return new NaoEncontradoException("Recurso nao encontrado");
        }

        return new Exception("Erro genérico");
    }
}