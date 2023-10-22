package org.primshits.currency_exchange.controller.exchange;

import org.primshits.currency_exchange.controller.BaseServlet;
import org.primshits.currency_exchange.dto.response.ExchangeResponse;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ExceptionHandler;
import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.util.JsonUtils;
import org.primshits.currency_exchange.util.ValidationUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchange")
public class Exchange extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = (req.getParameter("amount"));
        try {
            ValidationUtils.validateExchange(from,to,amount);
            double parsedAmount = Double.parseDouble(amount);
            ExchangeRate exchangeRate = exchangeRatesService.get(from,to);
            double rate = exchangeRate.getRate();
            double convertedAmount = parsedAmount * rate;
            ExchangeResponse exchangeResponse = new ExchangeResponse(exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency()
                    , rate
                    , parsedAmount
                    , convertedAmount);
            JsonUtils.write(resp,exchangeResponse);
        }catch (ApplicationException e){
            ExceptionHandler.handle(resp,e);
        }
    }
}
