package org.primshits.currency_exchange.servlets;

import org.primshits.currency_exchange.dao.CurrencyDAO;
import org.primshits.currency_exchange.models.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/currency/*")
public class ExactCurrency extends HttpServlet {
    private final CurrencyDAO currencyDAO;
    public ExactCurrency() {
        currencyDAO = new CurrencyDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String currencyCode = pathInfo.substring(1);
        Currency currency = currencyDAO.show(currencyCode);
        if(currency==null) {
            response.getWriter().print("Fuck you");
            return;
        }
        request.setAttribute("currency",currency);
        request.getRequestDispatcher("currency.jsp").forward(request,response);
    //TODO
    }
}
