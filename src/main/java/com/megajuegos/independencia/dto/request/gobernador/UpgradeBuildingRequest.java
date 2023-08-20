package com.megajuegos.independencia.dto.request.gobernador;

import com.megajuegos.independencia.service.util.PaymentRequestUtil;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class UpgradeBuildingRequest {

    private PaymentRequestUtil payment;
    private Long priceId;
}
