package com.megajuegos.independencia.config.auth.filter;

import com.megajuegos.independencia.config.auth.util.JwtTokenProvider;
import com.megajuegos.independencia.entities.UserIndependencia;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZACION_FOR_HEADER = "Authorization";
    private static final String STARTS_WITH_BEARER = "Bearer ";
    private static final int BEARER_PART_LENGHT = 7;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTHORIZACION_FOR_HEADER);
        final String jwt;
        final String email;
        if(authHeader == null || !authHeader.startsWith(STARTS_WITH_BEARER)){
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(BEARER_PART_LENGHT);
        email = jwtTokenProvider.extractUsername(jwt);

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            if(jwtTokenProvider.isTokenValid(jwt, (UserIndependencia) userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }

}
