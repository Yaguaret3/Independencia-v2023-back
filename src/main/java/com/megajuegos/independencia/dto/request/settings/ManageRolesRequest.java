package com.megajuegos.independencia.dto.request.settings;

import com.megajuegos.independencia.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ManageRolesRequest {

    Long id;
    RoleEnum role;
}
