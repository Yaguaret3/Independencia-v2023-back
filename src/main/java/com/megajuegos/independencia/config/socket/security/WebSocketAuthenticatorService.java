package com.megajuegos.independencia.config.socket.security;

import com.megajuegos.independencia.config.auth.util.JwtTokenProvider;
import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.repository.UserIndependenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WebSocketAuthenticatorService {

    private final JwtTokenProvider jwtTokenProvider;

    // This method MUST return a UsernamePasswordAuthenticationToken instance, the spring security chain is testing it with 'instanceof' later on. So don't use a subclass of it or any other class
    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  jwt) throws AuthenticationException {

        if (!jwtTokenProvider.isTokenValid(jwt)) {
            throw new AuthenticationCredentialsNotFoundException("Token was null or empty.");
        }

        return new UsernamePasswordAuthenticationToken(
                jwtTokenProvider.extractUsername(jwt),
                null,
                jwtTokenProvider.extractAuthorities(jwt));
    }
}
