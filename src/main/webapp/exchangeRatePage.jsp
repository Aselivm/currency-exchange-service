<%@ page import="org.primshits.currency_exchange.models.ExchangeRate" %><%--
  Created by IntelliJ IDEA.
  User: step6
  Date: 17.09.2023
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>ExchangeRate</title>
</head>
<body>
<%ExchangeRate exchangeRate = (ExchangeRate) request.getAttribute("exchangeRate");%>
<%=exchangeRate%>
<br/>
<hr/>
<form method="post" action="/exchangeRate/<%=exchangeRate.getBaseCurrency().getCode()+exchangeRate.getTargetCurrency().getCode()%>">
    <label for="amount">Change amount: </label>
    <input type="text" id="amount" name="amount"/>
    <button type="submit">Submit!</button>
</form>
</body>
</html>
