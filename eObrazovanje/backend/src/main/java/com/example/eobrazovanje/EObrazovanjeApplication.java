package com.example.eobrazovanje;

import com.example.eobrazovanje.ethereum.EObrazovanjeContractClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

@SpringBootApplication
public class EObrazovanjeApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(EObrazovanjeApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
