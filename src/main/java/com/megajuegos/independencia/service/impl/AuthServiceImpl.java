package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.config.auth.util.JwtTokenProvider;
import com.megajuegos.independencia.dto.request.auth.RegisterRequest;
import com.megajuegos.independencia.dto.request.auth.RenewPassRequest;
import com.megajuegos.independencia.dto.response.AuthenticateResponse;
import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.enums.RoleEnum;
import com.megajuegos.independencia.exceptions.CredentialsException;
import com.megajuegos.independencia.exceptions.EmailAlreadyExistsException;
import com.megajuegos.independencia.repository.UserIndependenciaRepository;
import com.megajuegos.independencia.service.AuthService;
import com.megajuegos.independencia.service.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.megajuegos.independencia.util.Messages.*;

@Service
public class AuthServiceImpl implements AuthService {

    private UserIndependenciaRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserUtil userUtil;


    @Autowired
    AuthServiceImpl(UserIndependenciaRepository userRepository,
                    PasswordEncoder passwordEncoder,
                    JwtTokenProvider jwtTokenProvider,
                    AuthenticationManager authenticationManager,
                    UserUtil userUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userUtil = userUtil;
    }

    @Override
    public AuthenticateResponse register(RegisterRequest request) {

        userRepository.findAll().forEach(u -> {
            if(u.getEmail().equalsIgnoreCase(request.getEmail())){
                throw new EmailAlreadyExistsException(request.getEmail());
            }
        });

        String encodedPass = passwordEncoder.encode(request.getPassword());

        UserIndependencia user = UserIndependencia.builder()
                .email(request.getEmail())
                .password(encodedPass)
                .username(request.getUsername())
                .roles(Stream.of(RoleEnum.USER).collect(Collectors.toSet()))
                .build();

        userRepository.save(user);
        String jwt = jwtTokenProvider.generateToken(user);

        return AuthenticateResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()))
                .token(jwt)
                .build();
    }

    @Override
    public AuthenticateResponse login(String email, String password) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        UserIndependencia user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_EMAIL));
        String jwt = jwtTokenProvider.generateToken(user);

        return AuthenticateResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()))
                .token(jwt)
                .build();
    }

    @Override
    public String renewPass(RenewPassRequest request) {

        UserIndependencia userDetails = userUtil.getCurrentUser();

        if(!passwordEncoder.matches(request.getOldPass(), userDetails.getPassword())){
            throw new CredentialsException(ORIGINAL_PASS_NOT_MATCHES);
        }

        userDetails.setPassword(passwordEncoder.encode(request.getNewPass()));
        userRepository.save(userDetails);

        return CONTRASEÑA_ACTUALIZADA_CON_ÉXITO;
    }

    @Override
    public AuthenticateResponse getCurrentUser() {

        UserIndependencia userDetails = userUtil.getCurrentUser();

        return AuthenticateResponse.builder()
                .email(userDetails.getEmail())
                .username(userDetails.getUsername())
                .roles(userDetails.getRoles().stream().map(Enum::name).collect(Collectors.toSet()))
                .build();
    }
}
