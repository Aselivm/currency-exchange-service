package org.primshits.currency_exchange.exceptions;

public class NotFoundException extends RuntimeException{

    @Deprecated
    public NotFoundException(String message) {
        super(message);
    }
}
