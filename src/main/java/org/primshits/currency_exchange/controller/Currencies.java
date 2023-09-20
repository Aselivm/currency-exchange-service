package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.models.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/currencies","/currency/*"})
public class Currencies extends CurrencyServlets {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        currencyService.delete(request.getPathInfo().substring(1));
        response.sendRedirect("/currencies");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String currencyCode = request.getPathInfo().substring(1);
            Currency currency = getCurrencyInfo(currencyCode);
            if(currency!=null){
                request.setAttribute("currency", currency);
                getServletContext().getRequestDispatcher("/currencyPage.jsp").forward(request, response);
            }else{
                    response.getWriter().write("Valyuta ne naydena");
            }
        } else {
            request.setAttribute("currencies",currencyService);
            request.getRequestDispatcher("currenciesList.jsp").forward(request, response);
        }
    }


    public Currency getCurrencyInfo(String currencyCode){
        return currencyService.show(currencyCode);
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
