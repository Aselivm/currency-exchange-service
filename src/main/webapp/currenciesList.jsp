<%@ page import="org.primshits.currency_exchange.models.Currency" %>
<%@ page import="java.util.List" %>
<%@ page import="org.primshits.currency_exchange.service.CurrencyService" %><%--
  Created by IntelliJ IDEA.
  User: step6
  Date: 07.09.2023
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Currencies</title>
</head>
<body>
<h1>Currencies list</h1>
<% CurrencyService currencyService = (CurrencyService)request.getAttribute("currencies");
    List<Currency> currencyList = currencyService.index();
    for(Currency currency : currencyList){
%>
<a href="<%= request.getContextPath() %>/currency/<%= currency.getCode() %>"><%= currency + "<br/>" %></a>
<%
    }
%>
<br/>
<hr/>
<a href="${pageContext.request.contextPath}/newCurrency">Add new currency</a>
<br/>
<a href="${pageContext.request.contextPath}/exchangeRates">Exchange page</a>
</body>
</html>
