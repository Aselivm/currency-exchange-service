package org.primshits.currency_exchange.servlets;

import org.primshits.currency_exchange.dao.CurrencyDAO;
import org.primshits.currency_exchange.models.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/currencies","/currency/*"})
public class Currencies extends HttpServlet {

    private final CurrencyDAO currencyDAO;
    public Currencies() {
        currencyDAO = new CurrencyDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String currencyCode = request.getPathInfo().substring(1);
            Currency currency = getCurrencyInfo(currencyCode);
            if(currency!=null) {
                request.setAttribute("currency",currency);
                getServletContext().getRequestDispatcher("/currency.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("currencies",currencyDAO);
            request.getRequestDispatcher("currencies.jsp").forward(request, response);
        }
    }


    public Currency getCurrencyInfo(String currencyCode){
        return currencyDAO.show(currencyCode);
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
