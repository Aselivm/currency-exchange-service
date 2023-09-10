<%@ page import="org.primshits.currency_exchange.servlets.Currencies" %>
<%@ page import="org.primshits.currency_exchange.models.Currency" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: step6
  Date: 07.09.2023
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Currencies</title>
</head>
<body>
<h1>Список валют</h1>
<% Currencies currencies = (Currencies)request.getAttribute("currencies");
    List<Currency> currencyList = currencies.getCurrencyDAO().index();
    for(Currency currency : currencyList){
%>
<%= currency + "<br/>"%>
<%
    }
%>
</body>
</html>
