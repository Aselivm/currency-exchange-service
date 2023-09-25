package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.models.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRatePage extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRate exchangeRate = getExchangeRateFromPath(req);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(),exchangeRate);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRate exchangeRate = getExchangeRateFromPath(req);
        exchangeRate.setRate(Double.parseDouble(req.getParameter("rate")));
        exchangeRatesService.update(exchangeRate.getId(), exchangeRate);
        objectMapper.writeValue(resp.getOutputStream(),exchangeRate);
    }
}
