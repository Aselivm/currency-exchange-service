package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.converter.ExchangeRateConverter;
import org.primshits.currency_exchange.dto.ExchangeRateDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchangeRates")
public class ExchangeRates extends BaseServlet {
    private ExchangeRateConverter exchangeRateConverter;

    @Override
    public void init() throws ServletException {
        super.init();
        exchangeRateConverter = new ExchangeRateConverter();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(),exchangeRatesService.getAll());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        double rate = Double.parseDouble(req.getParameter("rate"));
        ExchangeRateDTO exchangeRateDTO = exchangeRateConverter.putToDTO(baseCurrencyCode, targetCurrencyCode, rate);
        exchangeRatesService.save(exchangeRateConverter.convert(exchangeRateDTO));
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(),exchangeRatesService.get(baseCurrencyCode,targetCurrencyCode));
    }

}
