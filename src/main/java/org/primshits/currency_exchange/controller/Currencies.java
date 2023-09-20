package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.models.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/currencies")
public class Currencies extends CurrencyServlets {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("currencies", currencyService);
        req.getRequestDispatcher("currenciesList.jsp").forward(req, resp);
    }

}
