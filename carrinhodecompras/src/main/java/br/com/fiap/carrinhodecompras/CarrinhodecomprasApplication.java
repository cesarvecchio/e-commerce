package br.com.fiap.carrinhodecompras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CarrinhodecomprasApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrinhodecomprasApplication.class, args);
    }

}
