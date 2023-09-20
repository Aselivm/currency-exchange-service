<%@ page import="org.primshits.currency_exchange.models.ExchangeRate" %><%--
  Created by IntelliJ IDEA.
  User: step6
  Date: 17.09.2023
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>ConvertedAmount</title>
</head>
<body>
<%ExchangeRate exchangeRate = (ExchangeRate) request.getAttribute("exchangeRate");
    Double convertedAmount = (Double) request.getAttribute("convertedAmount");%>
<%=exchangeRate+"<br/>"+request.getParameter("amount")+"<br/>"+String.format("%.2f", convertedAmount)%>

</body>
</html>
