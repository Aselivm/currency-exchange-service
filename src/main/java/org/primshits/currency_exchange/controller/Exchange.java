package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.dto.response.ExchangeResponse;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ExceptionHandler;
import org.primshits.currency_exchange.models.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchange")
public class Exchange extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        double amount = Double.parseDouble(req.getParameter("amount"));
        try {
            ExchangeRate exchangeRate = exchangeRatesService.get(from,to);
            double rate = exchangeRate.getRate();
            double convertedAmount = amount * rate;
            ExchangeResponse exchangeResponse = new ExchangeResponse(exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency()
                    , rate
                    , amount
                    , convertedAmount);
            objectMapper.writeValue(resp.getOutputStream(), exchangeResponse);
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }
}
