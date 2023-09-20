package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.service.CurrencyService;
import org.primshits.currency_exchange.service.ExchangeRatesService;

import javax.servlet.http.HttpServlet;

public abstract class CurrencyServlets extends HttpServlet {

    protected CurrencyService currencyService;
    protected ExchangeRatesService exchangeRatesService;
    public CurrencyServlets() {
        currencyService = new CurrencyService();
        exchangeRatesService = new ExchangeRatesService();
    }
}
