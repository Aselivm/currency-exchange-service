package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.models.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchange")
public class Exchange extends CurrencyServlets{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");

        resp.sendRedirect("/exchange?from="+from+"&to="+to+"&amount="+amount);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");
            Currency baseCurrency = currencyService.show(from);
            Currency targetCurrency = currencyService.show(to);
            ExchangeRate exchangeRate = exchangeRatesService.show(baseCurrency.getId(), targetCurrency.getId());
        if(exchangeRate!=null){
            Double convertedAmount = Double.parseDouble(amount) * exchangeRate.getRate();
            req.setAttribute("exchangeRate", exchangeRate);
            req.setAttribute("convertedAmount", convertedAmount);
            req.getRequestDispatcher("exchange.jsp").forward(req, resp);
        }else{
            resp.getWriter().write("Valyuta ne naydena");
        }
    }
}
