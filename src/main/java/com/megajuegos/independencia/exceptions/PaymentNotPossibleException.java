package com.megajuegos.independencia.exceptions;

public class PaymentNotPossibleException extends RuntimeException{

    public PaymentNotPossibleException(){
        super("No se pudo realizar el pago");
    }
}
