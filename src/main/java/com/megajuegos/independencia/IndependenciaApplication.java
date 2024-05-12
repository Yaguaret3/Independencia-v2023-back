package com.megajuegos.independencia;

import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.enums.RoleEnum;
import com.megajuegos.independencia.repository.UserIndependenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
public class IndependenciaApplication implements CommandLineRunner {

    private final UserIndependenciaRepository userRepository;

    @Value("${ar.com.independencia.admin.email}")
    private String email;
    @Value("${ar.com.independencia.admin.username}")
    private String username;
    @Value("${ar.com.independencia.admin.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(IndependenciaApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Optional<UserIndependencia> admin = userRepository.findByEmail(email);
        if (admin.isPresent()) {
            return;
        }
        userRepository.save(
                UserIndependencia.builder()
                        .email(email)
                        .username(username)
                        .password(password)
                        .roles(Stream.of(RoleEnum.ADMIN).collect(Collectors.toList()))
                        .playerDataList(Stream.of(RoleEnum.CONTROL.createPlayerData()).collect(Collectors.toList()))
                        .build());
    }
}
