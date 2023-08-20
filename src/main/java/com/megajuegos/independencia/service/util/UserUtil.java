package com.megajuegos.independencia.service.util;

import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.repository.UserIndependenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserIndependenciaRepository userRepository;

    public UserIndependencia getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserIndependencia user = (UserIndependencia) auth.getPrincipal();

        return userRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException(""));
    }
}
