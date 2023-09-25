package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.converter.CurrencyConverter;
import org.primshits.currency_exchange.dto.CurrencyDTO;
import org.primshits.currency_exchange.exceptions.DatabaseException;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.exceptions.ErrorResponse;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");
        CurrencyDTO currencyDTO = currencyConverter.putToDTO(name, code, sign);
        currencyService.save(currencyConverter.convert(currencyDTO));
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(),currencyService.show(code));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            List<Currency> currencyList = currencyService.index();
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(),currencyList);
        }catch (DatabaseException e){
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            objectMapper.writeValue(resp.getOutputStream(), new ErrorResponse("The database is unavailable"));
        }
    }

}
