
<%-- 
    Document   : head
    Created on : Apr 30, 2021, 4:14:24 PM
    Author     : Vinayak
--%>

<%@tag description="the head section of my jsps" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
<%@ attribute name="htmlTitle" type="java.lang.String" required="true" %>
<%@ include file="/WEB-INF/jspf/base.jspf" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link href="styles/main.css" rel="stylesheet">
</head>