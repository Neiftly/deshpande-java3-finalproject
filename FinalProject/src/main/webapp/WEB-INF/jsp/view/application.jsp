<%-- 
    Document   : application
    Created on : Apr 13, 2021, 8:10:45 PM
    Author     : Vinayak
--%>
<%--@elvariable id="job" type="com.deshpande.Job"--%>
<%--@elvariable id="application" type="com.deshpande.Application"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <t:head htmlTitle="Application #${application.id}"></t:head>
        <body>
        <t:nav></t:nav>
            <div class="container">
                <h1><c:out value="Application #${application.id}" /></h1>
                
                <p>Job Title:&emsp; <c:out value="${application.jobTitle}" /></p>
                <p>Name:&emsp; <c:out value="${application.firstName} ${application.lastName}"/></p>
                <p>Email:&emsp; <c:out value="${application.email}" /></p>
                <p>Phone:&emsp; <c:out value="(${fn:substring(application.phone, 0, 3)}) 
                       ${fn:substring(application.phone, 4, 7)} -
                       ${fn:substring(application.phone, 8, 12)}" /></p>    
                <p>Resume:&emsp; <a href="<c:url value="/applications">
                                        <c:param name="action" value="download" />
                                    </c:url>"><c:out value="${application.resumeUpload.name}" /></a>
                </p>
                <c:set var = "salary" value="${fn:escapeXml(application.desiredSalary)}"/>
                <p>Desired Salary:&emsp;
                    <fmt:setLocale value="en_US" />
                    <fmt:formatNumber value="${salary}" type="currency" />                 
                </p>
                <c:set var="eSD" value="${application.earliestStartDate}" />
                <p>Earliest Start Date:&emsp;
                    <fmt:formatDate value="${eSD}" />
                </p>
                <form method="GET" action="<c:url value="/applications" />"  >                                                      
                    <input type="submit" value="Mark Inactive"/>
                    <input type="hidden" name="action" value="deactivate" />
                </form>
            </div>
    </body>
</html>
