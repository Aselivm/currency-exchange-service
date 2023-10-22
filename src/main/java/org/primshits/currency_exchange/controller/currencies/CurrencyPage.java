package org.primshits.currency_exchange.controller.currencies;

import org.primshits.currency_exchange.controller.BaseServlet;
import org.primshits.currency_exchange.dto.response.ErrorResponse;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ErrorMessage;
import org.primshits.currency_exchange.exceptions.ExceptionHandler;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.util.CurrencyUtils;
import org.primshits.currency_exchange.util.InputStringUtils;
import org.primshits.currency_exchange.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/currency/*")
public class CurrencyPage extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currencyCode = InputStringUtils.parsePathInfo(req);
        try{
            ValidationUtils.validateCurrency(currencyCode);
            Currency currency = currencyService.get(currencyCode);
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(),currency);
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }

}
