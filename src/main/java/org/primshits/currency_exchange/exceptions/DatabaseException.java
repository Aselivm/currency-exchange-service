package org.primshits.currency_exchange.exceptions;

public class DatabaseException extends ApplicationException{
    public DatabaseException() {
        super(ErrorMessage.INTERNAL_DATABASE_ERROR);
    }
}
