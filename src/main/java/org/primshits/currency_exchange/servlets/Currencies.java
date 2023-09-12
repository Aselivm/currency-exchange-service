package org.primshits.currency_exchange.servlets;

import org.primshits.currency_exchange.models.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/currencies","/currency/*"})
public class Currencies extends CurrencyServlets {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        currencySerivce.delete(req.getPathInfo().substring(1));
        resp.sendRedirect("/currencies");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        currencySerivce.delete(request.getPathInfo().substring(1));
        response.sendRedirect("/currencies");
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
            request.setAttribute("currencies",currencySerivce);
            request.getRequestDispatcher("currencies.jsp").forward(request, response);
        }
    }


    public Currency getCurrencyInfo(String currencyCode){
        return currencySerivce.show(currencyCode);
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
