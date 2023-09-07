<%@ page import="org.primshits.currency_exchange.servlets.Currencies" %><%--
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
    <% Currencies currencies = new Currencies(); %>
    <%= currencies.getCurrencyDAO().index() %>
</body>
</html>
