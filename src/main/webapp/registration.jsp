<%--
  Created by IntelliJ IDEA.
  User: macbookair
  Date: 11.03.2024
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Registration</title>
</head>
<body>
<p>Регистрация нового пользователя системы</p>

<form action="registration" method="POST">
    Email: <input type="text" name="email"/>
    Login: <input type="text" name="login"/>
    Password: <input type="password" name="password"/>
    <input type="submit" value="Зарегистрироваться">
</form>
<a href="login">Log in</a>
</body>
</html>