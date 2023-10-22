package org.primshits.currency_exchange.controller.exchange;

import org.primshits.currency_exchange.controller.BaseServlet;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ExceptionHandler;
import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.util.InputStringUtils;
import org.primshits.currency_exchange.util.JsonUtils;
import org.primshits.currency_exchange.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRatePage extends BaseServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (!method.equals("PATCH")) {
            super.service(req, resp);
            return;
        }
        doPatch(req,resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String currencyPair = InputStringUtils.parsePathInfo(req);
        try {
            ValidationUtils.validateExchangeRate(currencyPair);
            ExchangeRate exchangeRate = exchangeRatesService.getExchangeRateFromCurrencyPair(currencyPair);
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtils.write(resp,exchangeRate);
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String currencyPair = InputStringUtils.parsePathInfo(req);

        String baseCurrencyCode = currencyPair.substring(0,3);
        String targetCurrencyCode = currencyPair.substring(3);

        String bodyParams = req.getReader().readLine();
        String firstBodyParam = bodyParams.split("&")[0];
        String rate = firstBodyParam.replace("rate=", "");
        try{
            ValidationUtils.validateExchange(baseCurrencyCode,targetCurrencyCode,rate);
            double parsedRate = Double.parseDouble(rate);
            ExchangeRate exchangeRate = exchangeRatesService.updateRate(baseCurrencyCode, targetCurrencyCode, parsedRate);
            JsonUtils.write(resp,exchangeRate);
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }
}
