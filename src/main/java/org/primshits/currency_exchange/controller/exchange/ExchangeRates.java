package org.primshits.currency_exchange.controller.exchange;

import org.primshits.currency_exchange.controller.BaseServlet;
import org.primshits.currency_exchange.dto.ExchangeRateDTO;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ExceptionHandler;
import org.primshits.currency_exchange.util.JsonUtils;
import org.primshits.currency_exchange.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchangeRates")
public class ExchangeRates extends BaseServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtils.write(resp,exchangeRatesService.getAll());
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");
        try{
            ValidationUtils.validateExchange(baseCurrencyCode,targetCurrencyCode,rate);
            double parsedRate = Double.parseDouble(rate);
            ExchangeRateDTO exchangeRateDTO = exchangeRatesService.putToDTO(baseCurrencyCode, targetCurrencyCode, parsedRate);
            exchangeRatesService.save(exchangeRatesService.convert(exchangeRateDTO));
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtils.write(resp,exchangeRatesService.get(baseCurrencyCode,targetCurrencyCode));
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }

}
