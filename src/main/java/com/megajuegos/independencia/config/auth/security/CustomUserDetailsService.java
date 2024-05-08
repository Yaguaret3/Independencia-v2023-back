package com.megajuegos.independencia.config.auth.security;

import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.enums.RoleEnum;
import com.megajuegos.independencia.exceptions.EmailNotFoundException;
import com.megajuegos.independencia.repository.UserIndependenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserIndependenciaRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserIndependencia user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));

        return new User(user.getEmail(), user.getPassword(), mappRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mappRoles(List<RoleEnum> roles) {
        return roles.stream().map(rol -> new SimpleGrantedAuthority(String.format("ROLE_%s",rol.name()))).collect(Collectors.toList());
    }
}
