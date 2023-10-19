package org.primshits.currency_exchange.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    INTERNAL_DATABASE_ERROR("Internal database error",HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    INTERNAL_ERROR("Internal error",HttpServletResponse.SC_INTERNAL_SERVER_ERROR),

    CURRENCY_ALREADY_EXISTS("Currency with this code already exists", HttpServletResponse.SC_CONFLICT),
    CURRENCY_NOT_FOUND("Currency not found", HttpServletResponse.SC_NOT_FOUND),
    BAD_CURRENCY_CODE_REQUEST("Bad currency code request",HttpServletResponse.SC_BAD_REQUEST),
    INVALID_CURRENCY_INPUT("Currency form is filled out incorrectly",HttpServletResponse.SC_BAD_REQUEST),

    EXCHANGE_RATE_ALREADY_EXISTS("Exchange rate already exists", HttpServletResponse.SC_CONFLICT),
    EXCHANGE_RATE_NOT_FOUND("Exchange rate with this currency codes is not found", HttpServletResponse.SC_NOT_FOUND),
    INVALID_EXCHANGE_RATE_INPUT("Exchange rate form is filled out incorrectly", HttpServletResponse.SC_BAD_REQUEST),
    INVALID_EXCHANGE_AMOUNT_INPUT("Bad exchange amount", HttpServletResponse.SC_BAD_REQUEST);



    private final String message;
    private final int status;
}
