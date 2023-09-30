package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.dto.response.ExchangeResponse;
import org.primshits.currency_exchange.models.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchange")
public class Exchange extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        double amount = Double.parseDouble(req.getParameter("amount"));

        //TODO реализовать
        ExchangeRate exchangeRate = exchangeRatesService.get(from,to);
        double rate = 0;
        try {
            rate = exchangeRate.getRate();
        }catch (Exception e){
            //TODO
        }
        double convertedAmount = amount * rate;
        ExchangeResponse exchangeResponse = new ExchangeResponse(exchangeRate.getBaseCurrency(),exchangeRate.getTargetCurrency()
                ,rate
                ,amount
                ,convertedAmount);
        objectMapper.writeValue(resp.getOutputStream(), exchangeResponse);
    }
}
