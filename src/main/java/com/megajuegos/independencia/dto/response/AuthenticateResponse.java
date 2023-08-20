package com.megajuegos.independencia.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthenticateResponse {

    String username;
    String email;
    Set<String> roles;
    String token;
}
