package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.models.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/currency/*")
public class CurrencyPage extends CurrencyServlets {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        currencyService.delete(req.getPathInfo().substring(1));
        resp.sendRedirect("/currencies");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currencyCode = req.getPathInfo().substring(1);
        Currency currency = getCurrencyInfo(currencyCode);
        if(currency!=null){
            req.setAttribute("currency", currency);
            getServletContext().getRequestDispatcher("/currencyPage.jsp").forward(req, resp);
        }else{
            resp.getWriter().write("Valyuta ne naydena");
        }
    }

    public Currency getCurrencyInfo(String currencyCode){
        return currencyService.show(currencyCode);
    }
}
