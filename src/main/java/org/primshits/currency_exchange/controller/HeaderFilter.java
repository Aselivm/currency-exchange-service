package org.primshits.currency_exchange.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(
        urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "paramName", value = "paramValue")
        }
)
public class HeaderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
