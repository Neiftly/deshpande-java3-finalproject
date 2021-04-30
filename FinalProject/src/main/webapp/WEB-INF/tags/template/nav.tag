<%-- 
    Document   : nav
    Created on : Apr 30, 2021, 4:25:45 PM
    Author     : Vinayak
--%>

<%@tag description="nav section" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
<%@ include file="/WEB-INF/jspf/base.jspf" %>
<nav>
    <a href="<c:url value="/jobs"/>">View Jobs</a>
    <a href="<c:url value="/applications"/>">Applications</a>
</nav>