package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.dto.CurrencyDTO;
import org.primshits.currency_exchange.models.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/newCurrency")
public class newCurrency extends CurrencyServlets {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String name = req.getParameter("name");
            String code = req.getParameter("code");
            String sign = req.getParameter("sign");
            CurrencyDTO currencyDTO = putToDTO(name, code, sign);
            currencyService.save(convertToModel(currencyDTO));
            resp.sendRedirect("/currencies");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/newCurrency.jsp").forward(req,resp);
    }

    private CurrencyDTO putToDTO(String name, String code, String sign){
        CurrencyDTO currency = new CurrencyDTO();
        currency.setFullName(name);
        currency.setCode(code);
        currency.setSign(sign);
        return currency;
    }

    private Currency convertToModel(CurrencyDTO currencyDTO){
        Currency currency = new Currency();
        currency.setId(currencyDTO.getId());
        currency.setSign(currencyDTO.getSign());
        currency.setCode(currencyDTO.getCode());
        currency.setFullName(currencyDTO.getFullName());
        return currency;
    }
}
