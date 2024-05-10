package com.megajuegos.independencia.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthenticateResponse {

    private String username;
    private String email;
    private Set<String> roles;
    private String token;
    private boolean isPlayerAllowed;
}
