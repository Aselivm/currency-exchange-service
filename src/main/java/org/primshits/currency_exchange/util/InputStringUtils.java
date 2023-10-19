package org.primshits.currency_exchange.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InputStringUtils {

    public static String parsePathInfo(HttpServletRequest request) {
        return request.getPathInfo().substring(1).toUpperCase();
    }

    public static boolean isEmptyField(String s1, String s2, String s3) {
        return s1.isEmpty() || s2.isEmpty() || s3.isEmpty();
    }

}
