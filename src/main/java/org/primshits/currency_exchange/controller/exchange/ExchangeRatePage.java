package org.primshits.currency_exchange.controller.exchange;

import org.primshits.currency_exchange.controller.BaseServlet;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ExceptionHandler;
import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.util.InputStringUtils;
import org.primshits.currency_exchange.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRatePage extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String currencyPair = InputStringUtils.parsePathInfo(req);
        try {
            ValidationUtils.validateExchangeRate(currencyPair);
            ExchangeRate exchangeRate = exchangeRatesService.getExchangeRateFromCurrencyPair(currencyPair);
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(), exchangeRate);
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String currencyPair = InputStringUtils.parsePathInfo(req);

        String baseCurrencyCode = currencyPair.substring(0,3);
        String targetCurrencyCode = currencyPair.substring(3);
        String rate = req.getParameter("rate");
        try{
            ValidationUtils.validateExchangeRate(baseCurrencyCode,targetCurrencyCode,rate);
            double parsedRate = Double.parseDouble(req.getParameter("rate"));
            exchangeRatesService.updateRate(baseCurrencyCode, targetCurrencyCode, parsedRate);
            ExchangeRate exchangeRate = exchangeRatesService.get(baseCurrencyCode,targetCurrencyCode);
            objectMapper.writeValue(resp.getOutputStream(),exchangeRate);
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }
}
