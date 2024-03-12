<%--
  Created by IntelliJ IDEA.
  User: macbookair
  Date: 20.02.2024
  Time: 17:56
  To change this template use File | Settings | File Templates.
  path /Users/macbookair/Tomcat/Tomcat10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="java.time.chrono.ChronoLocalDateTime" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    String path = request.getParameter("path");
    Path parentpath = Paths.get(path).getParent();
    String parentPath = parentpath.toString();
%><%!
    public String formatTime(File file) {
        Date date = new java.util.Date(file.lastModified());
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
%>
<html>
<head>
    <title><FirstJSP></title>
</head>
<body>
<form action="files" method="POST">
    <input type="submit" value="Выйти" id="logoutButton">
</form>
    <h3><%=new Date()%></h3>
    <h1><%=path%></h1>
    <hr/>
    <a href="<%="?path=" + parentPath%>">Up</a>
    <table>
        <tr>
            <td>File</td>
            <td>Size</td>
            <td>Date</td>
        </tr>
    <%
        File[] directories = (File[]) request.getAttribute("directories");
        for (File directory : directories){
    %>
        <tr>
            <td>
                <a href="<%="files?path=" + directory.getAbsolutePath()%>"><%=directory.getName() + "/"%></a>
            </td>
            <td>
                <h2>-------</h2>
            </td>
            <td>
                <h2><%=formatTime(directory)%></h2>
            </td>
        </tr>
    <%
        }
    %>
        <%
            File[] files = (File[]) request.getAttribute("files");
            for (File file : files) {
        %>
        <tr>
            <th>
                <a href=<%="download?path=" + file.getAbsolutePath()%>><%=file.getName()%></a>
            </th>
            <th><%=file.length()%></th>
            <th><%=formatTime(file)%></th>
        </tr>
        <%}%>
    </table>
</body>
</html>
