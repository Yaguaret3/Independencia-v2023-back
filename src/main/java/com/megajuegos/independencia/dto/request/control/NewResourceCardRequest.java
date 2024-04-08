package com.megajuegos.independencia.dto.request.control;

import com.megajuegos.independencia.enums.ResourceTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewResourceCardRequest {

    private ResourceTypeEnum resourceType;
}
