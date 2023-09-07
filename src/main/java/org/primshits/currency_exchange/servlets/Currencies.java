package org.primshits.currency_exchange.servlets;

import org.primshits.currency_exchange.dao.CurrencyDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/currencies")
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
        request.getRequestDispatcher("currencies.jsp").forward(request,response);
    }

    public CurrencyDAO getCurrencyDAO() {
        return currencyDAO;
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
