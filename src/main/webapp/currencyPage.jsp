
<%@ page import="org.primshits.currency_exchange.models.Currency" %><%--
  Created by IntelliJ IDEA.
  User: step6
  Date: 28.08.2023
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Currency</title>
</head>
<body>
<%;
Currency currency = (Currency)request.getAttribute("currency");%>
<%= currency%>
<hr/>
<form method="POST" action="/currency/<%=currency.getCode()%>">
    <input type="hidden" name="_method" value="DELETE">
    <button type="submit">delete</button>
</form>
</body>
</html>
