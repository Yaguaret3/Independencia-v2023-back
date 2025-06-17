package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class PaymentNotPossibleException extends CustomRuntimeException {

    public PaymentNotPossibleException(){
        super("No se pudo realizar el pago");
    }
}
