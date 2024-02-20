<%--
  Created by IntelliJ IDEA.
  User: macbookair
  Date: 20.02.2024
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<%
    String path = request.getParameter("path");
%>
<html>
<head>
    <title><FirstJSP></title>
</head>
<body>
    <h3><%=new Date()%></h3>
    <h1><%=path%></h1>
</body>
</html>
