<%--
  Created by IntelliJ IDEA.
  User: step6
  Date: 11.09.2023
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Add new currency</title>
</head>
<body>
<h1>Adding new currency</h1>
<form method="post" action="/newCurrency">
  <label for="name">Name</label>
  <input type="name" id="name" name="name" ><br>

  <label for="code">Code</label>
  <input type="code" id="code" name="code"><br>

  <label for="sign">Sign</label>
  <input type="sign" id="sign" name="sign"><br>

  <button type="submit">Submit!</button>
</form>
</body>
</html>
