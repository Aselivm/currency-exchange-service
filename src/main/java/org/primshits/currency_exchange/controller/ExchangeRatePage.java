package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRatePage extends CurrencyServlets{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        changeAmount(req,resp);
    }

    private void changeAmount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String baseCurrencyCode = pathInfo.substring(1, 4);
        String targetCurrencyCode = pathInfo.substring(4);
        Currency baseCurrency = currencyService.show(baseCurrencyCode);
        Currency targetCurrency = currencyService.show(targetCurrencyCode);
        ExchangeRate exchangeRate = exchangeRatesService.show(baseCurrency.getId(), targetCurrency.getId());
        if (exchangeRate != null) {

            exchangeRate.setRate(Double.parseDouble(req.getParameter("amount")));
            exchangeRatesService.update(exchangeRate.getId(), exchangeRate);
            resp.sendRedirect("/exchangeRate" + req.getPathInfo());
        } else {
            resp.getWriter().write("Valyuta ne naydena");
        }
    }
}
