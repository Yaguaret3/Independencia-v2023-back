package com.megajuegos.independencia.service;

import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.enums.PersonalPricesEnum;
import com.megajuegos.independencia.service.util.PaymentRequestUtil;

public interface PaymentService {

    Boolean succesfulPay(PlayerData playerData, PaymentRequestUtil request, PersonalPricesEnum toBuy);
}
