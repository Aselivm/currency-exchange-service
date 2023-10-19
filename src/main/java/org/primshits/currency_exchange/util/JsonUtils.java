package org.primshits.currency_exchange.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.primshits.currency_exchange.exceptions.ApplicationException;
import org.primshits.currency_exchange.exceptions.ErrorMessage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void write(HttpServletResponse response, Object obj){
        try {
            mapper.writeValue(response.getOutputStream(),obj);
        } catch (IOException e) {
            throw new ApplicationException(ErrorMessage.INTERNAL_ERROR);
        }
    }
}
