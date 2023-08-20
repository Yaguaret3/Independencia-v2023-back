package com.megajuegos.independencia.dto.request.mercader;

import com.megajuegos.independencia.service.util.PaymentRequestUtil;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PricesRequest {

    private PaymentRequestUtil payment;
    private Integer resourceType;
}
