package com.megajuegos.independencia.config.auth.security;

import com.megajuegos.independencia.config.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()

                //PUBLIC
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.GET, "/src/assets/img/**").permitAll()
                .antMatchers(HttpMethod.GET, "/assets/**").permitAll()
                .antMatchers(HttpMethod.GET, "/img/**").permitAll()

                //ACTUATOR
                .antMatchers("/actuator/**").permitAll()


                //LOGIN
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/register").permitAll()
                //GOBERNADOR
                .antMatchers("/gobernador").permitAll()
                .antMatchers("/api/ciudad/**").hasRole(GOBERNADOR.name())
                //CAPITAN
                .antMatchers("/capitan").permitAll()
                .antMatchers("/api/militares/**").hasRole(CAPITAN.name())
                //MERCADER
                .antMatchers("/mercader").permitAll()
                .antMatchers("/api/comercio/**").hasRole(MERCADER.name())
                //REVOLUCIONARIO
                .antMatchers("/revolucionario").permitAll()
                .antMatchers("/api/revolucion/**").hasRole(REVOLUCIONARIO.name())
                //CONTROL
                .antMatchers("/control").permitAll()
                .antMatchers("/api/control/**").hasRole(CONTROL.name())
                //ADMIN
                .antMatchers("/settings").permitAll()
                .antMatchers("/api/settings/**").hasRole(ADMIN.name())
                //WEBSOCKETS
                .antMatchers("/ws").permitAll()
                .antMatchers("/ws/**").permitAll()
                //PLAYER
                .antMatchers("/api/player/**").authenticated()

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
