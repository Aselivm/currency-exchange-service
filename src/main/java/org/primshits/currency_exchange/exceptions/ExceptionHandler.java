package org.primshits.currency_exchange.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.primshits.currency_exchange.util.JsonUtils;

import javax.servlet.http.HttpServletResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionHandler {
    public static void handle(HttpServletResponse response, ApplicationException e){
        response.setStatus(e.getError().getStatus());
        JsonUtils.write(response,e.getError().getMessage());
    }
}
