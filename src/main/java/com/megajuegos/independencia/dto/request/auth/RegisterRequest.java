package com.megajuegos.independencia.dto.request.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

    String username;
    String password;
    String email;
}
