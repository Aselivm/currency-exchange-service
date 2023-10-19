package org.primshits.currency_exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.primshits.currency_exchange.service.CurrencyService;
import org.primshits.currency_exchange.service.ExchangeRatesService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class BaseServlet extends HttpServlet {
    protected ObjectMapper objectMapper;
    protected CurrencyService currencyService;
    protected ExchangeRatesService exchangeRatesService;

    @Override
    public void init() throws ServletException {
        super.init();
        currencyService = new CurrencyService();
        exchangeRatesService = new ExchangeRatesService();
        objectMapper = new ObjectMapper();
    }

}
