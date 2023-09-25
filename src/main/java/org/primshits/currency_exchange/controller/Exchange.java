package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.dto.ExchangeDTO;
import org.primshits.currency_exchange.models.Currency;

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

        Currency baseCurrency = currencyService.show(from);
        Currency targetCurrency = currencyService.show(to);
        double rate = 0;
        try {
            rate = exchangeRatesService.show(baseCurrency.getId(), targetCurrency.getId()).getRate();
        }catch (Exception e){
            //TODO
        }
        double convertedAmount = amount * rate;
        ExchangeDTO exchangeDTO = new ExchangeDTO(baseCurrency,targetCurrency,rate,amount,convertedAmount);
        objectMapper.writeValue(resp.getOutputStream(),exchangeDTO);
    }
}
