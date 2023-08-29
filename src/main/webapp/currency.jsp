<%--
  Created by IntelliJ IDEA.
  User: step6
  Date: 28.08.2023
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>First JSP</title>
</head>
<body>
<h1>Testing JSP</h1>
<p>
    <%@ page import="org.primshits.currency_exchange.Info" %>
    <% Info info = new Info();%>
    <%=info.info()%>

    <% String name = request.getParameter("name");String surname = request.getParameter("surname");%>
    <%="Hello " + name +" "+surname%>
</p>
</body>
</html>
