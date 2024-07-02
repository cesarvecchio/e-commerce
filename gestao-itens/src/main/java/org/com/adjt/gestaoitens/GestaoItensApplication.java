package org.com.adjt.gestaoitens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GestaoItensApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoItensApplication.class, args);
    }

}
