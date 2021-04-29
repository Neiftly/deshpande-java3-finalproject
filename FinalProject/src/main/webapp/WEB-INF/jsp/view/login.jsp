<%-- 
    Document   : login
    Created on : Apr 13, 2021, 8:10:32 PM
    Author     : Vinayak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Jobs</title>
    </head>
    <body>
        <h2>Login</h2>
        <p>You must log in to access the songs.</p>
        <%
            if ((Boolean) request.getAttribute("loginFailed")) {
        %>
        <p style="font-weight: bold;">The username or password you entered are not correct. Please try again.</p>
        <%
            }
        %>
        <form method="POST" action="<c:url value="/login" />">
            <label for="username">Username</label>
            <input type="text" name="username" id="username" /><br><br>
            <label for="password">Password</label>
            <input type="password" name="password" id="password" /><br><br>
            <input type="submit" value="Log In" />
        </form>
    </body>
</html>
