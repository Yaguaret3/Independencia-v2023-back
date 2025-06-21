package com.megajuegos.independencia.service.util;

import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.repository.UserIndependenciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUtil {

    private final UserIndependenciaRepository userRepository;

    public UserIndependencia getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getPrincipal().toString();

        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(""));
    }
}
