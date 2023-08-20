package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.auth.RegisterRequest;
import com.megajuegos.independencia.dto.request.auth.RenewPassRequest;
import com.megajuegos.independencia.dto.response.AuthenticateResponse;

public interface AuthService {

    AuthenticateResponse register(RegisterRequest registerRequest);
    AuthenticateResponse login(String email, String password);
    String renewPass(RenewPassRequest request);
    AuthenticateResponse getCurrentUser();
}
