<%@ page import="org.primshits.currency_exchange.servlets.Currencies" %>
<%@ page import="org.primshits.currency_exchange.models.Currency" %><%--
  Created by IntelliJ IDEA.
  User: step6
  Date: 28.08.2023
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Currency</title>
</head>
<body>
<%;
Currency currency = (Currency)request.getAttribute("currency");%>
<%= currency.getFullName()%>
</body>
</html>
