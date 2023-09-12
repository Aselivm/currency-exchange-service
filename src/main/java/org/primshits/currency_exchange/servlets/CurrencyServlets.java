package org.primshits.currency_exchange.servlets;

import org.primshits.currency_exchange.dao.CurrencyDAO;
import org.primshits.currency_exchange.service.CurrencyService;

import javax.servlet.http.HttpServlet;

public abstract class CurrencyServlets extends HttpServlet {

    protected CurrencyService currencySerivce;
    public CurrencyServlets() {
        currencySerivce = new CurrencyService();
    }
}
