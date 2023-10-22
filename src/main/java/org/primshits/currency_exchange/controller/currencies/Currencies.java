package org.primshits.currency_exchange.controller.currencies;

import org.primshits.currency_exchange.controller.BaseServlet;
import org.primshits.currency_exchange.dto.CurrencyDTO;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ExceptionHandler;
import org.primshits.currency_exchange.models.Currency;
import org.primshits.currency_exchange.util.JsonUtils;
import org.primshits.currency_exchange.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/currencies")
public class Currencies extends BaseServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");
        try {
            ValidationUtils.validateCurrency(name,code,sign);
            CurrencyDTO currencyDTO = currencyService.putToDTO(name, code, sign);
            Currency currency = currencyService.save(currencyService.convert(currencyDTO));
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtils.write(resp,currency);
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<Currency> currencyList = currencyService.getAll();
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtils.write(resp,currencyList);
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }

}
