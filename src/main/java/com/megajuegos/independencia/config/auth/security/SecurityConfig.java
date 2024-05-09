package com.megajuegos.independencia.config.auth.security;

import com.megajuegos.independencia.config.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.megajuegos.independencia.enums.RoleEnum.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()

                //LOGIN
                .antMatchers("/api/auth/**").permitAll()

                //GOBERNADOR
                .antMatchers("/api/ciudad").hasRole(GOBERNADOR.name())
                .antMatchers("/api/ciudad/**").hasRole(GOBERNADOR.name())
                //CAPITAN
                .antMatchers("/api/militares").hasRole(CAPITAN.name())
                .antMatchers("/api/militares/**").hasRole(CAPITAN.name())
                //MERCADER
                .antMatchers("/api/comercio").hasRole(MERCADER.name())
                .antMatchers("/api/comercio/**").hasRole(MERCADER.name())
                //REVOLUCIONARIO
                .antMatchers("/api/revolucion").hasRole(REVOLUCIONARIO.name())
                .antMatchers("/api/revolucion/**").hasRole(REVOLUCIONARIO.name())
                //CONTROL
                .antMatchers("/api/control").hasRole(CONTROL.name())
                .antMatchers("/api/control/**").hasRole(CONTROL.name())
                //ADMIN
                //.antMatchers("/api/settings/**").hasRole(ADMIN.name())
                //WEBSOCKETS
                .antMatchers("/ws").permitAll()
                .antMatchers("/ws/**").permitAll()

                .anyRequest().denyAll()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public WebMvcConfigurer configurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:5173");
            }
        };
    }

}
