package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.dao.ExchangeRatesDAO;
import org.primshits.currency_exchange.dto.CurrencyDTO;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchangeRates")
public class ExchangeRates extends CurrencyServlets{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setAttribute("exchangeRatesService", exchangeRatesService);
            req.getRequestDispatcher("exchangeRatesList.jsp").forward(req, resp);
    }

}
