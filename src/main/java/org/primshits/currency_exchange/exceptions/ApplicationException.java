package org.primshits.currency_exchange.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorMessage error;
}
