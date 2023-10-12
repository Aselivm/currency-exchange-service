package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.dto.response.ErrorResponse;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.util.CurrencyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/currency/*")
public class CurrencyPage extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currencyCode = req.getPathInfo().substring(1); //TODO в утил
        if(currencyCode.length()!=3){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getOutputStream(),new ErrorResponse("Bad currency code request"));
        }
        Currency currency = currencyService.get(currencyCode);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(),currency);
    }

}
