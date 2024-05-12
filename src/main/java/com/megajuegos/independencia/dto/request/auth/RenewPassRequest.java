package com.megajuegos.independencia.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenewPassRequest {

    private String email;
    private String oldPass;
    private String newPass;
}
