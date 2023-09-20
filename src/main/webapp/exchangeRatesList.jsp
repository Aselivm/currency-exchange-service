<%@ page import="org.primshits.currency_exchange.service.ExchangeRatesService" %>
<%@ page import="org.primshits.currency_exchange.models.ExchangeRate" %><%--
  Created by IntelliJ IDEA.
  User: step6
  Date: 12.09.2023
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Exchange Rates</title>
</head>
<body>
    <h1>Make an exchange!</h1>
    <form method="post" action="${pageContext.request.contextPath}/exchange">
        <label for="baseCurrency">Base currency code: </label>
        <input type="text" id="baseCurrency" name="from"/>
        <label for="targetCurrency">Target currency code: </label>
        <input type="text" id="targetCurrency" name="to"/>
        <label for="amount">Amount: </label>
        <input type="text" id="amount" name="amount"/>
        <button type="submit">Exchange!!</button>
    </form>
<br/>
<hr/>
<%ExchangeRatesService exchangeRatesService = (ExchangeRatesService) request.getAttribute("exchangeRatesService");
for(ExchangeRate exchangeRate : exchangeRatesService.index()){%>
<a href="<%= request.getContextPath() %>/exchangeRate/<%=exchangeRate.getBaseCurrency().getCode()+exchangeRate.getTargetCurrency().getCode()%>">
    <%=exchangeRate + "<br/>" %>
</a>
<%}%>
</body>
</html>
