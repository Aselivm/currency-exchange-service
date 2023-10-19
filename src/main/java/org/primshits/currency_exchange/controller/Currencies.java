package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.converter.CurrencyConverter;
import org.primshits.currency_exchange.dto.CurrencyDTO;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ExceptionHandler;
import org.primshits.currency_exchange.models.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/currencies")
public class Currencies extends BaseServlet {
    private CurrencyConverter currencyConverter;
    @Override
    public void init() throws ServletException {
        super.init();
        currencyConverter = new CurrencyConverter();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        //TODO put isUnique here
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");
        try {
            CurrencyDTO currencyDTO = currencyConverter.putToDTO(name, code, sign);
            currencyService.save(currencyConverter.convert(currencyDTO));
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(), currencyService.get(code));
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<Currency> currencyList = currencyService.getAll();
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(),currencyList);
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }

}
