package com.megajuegos.independencia.dto.request.capitan;

import com.megajuegos.independencia.service.util.PaymentRequestUtil;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BuyRequest {

    private PaymentRequestUtil payment;
    private Integer cardTypeId;

}
