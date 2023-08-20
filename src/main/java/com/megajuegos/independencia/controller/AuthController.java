package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.auth.LoginRequest;
import com.megajuegos.independencia.dto.request.auth.RegisterRequest;
import com.megajuegos.independencia.dto.request.auth.RenewPassRequest;
import com.megajuegos.independencia.dto.response.AuthenticateResponse;
import com.megajuegos.independencia.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticateResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> login(@RequestBody LoginRequest login) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(login.getEmail(), login.getPassword()));
    }

    @PostMapping("/renew-pass")
    public ResponseEntity<String> renewPass(@RequestBody RenewPassRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(authService.renewPass(request));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthenticateResponse> getCurrentUser(){
        return ResponseEntity.status(HttpStatus.OK).body(authService.getCurrentUser());
    }

}
