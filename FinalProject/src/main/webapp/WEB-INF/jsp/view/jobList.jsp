<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- 
    Document   : jobList
    Created on : Apr 13, 2021, 7:46:06 PM
    Author     : Vinayak
--%>
<jsp:useBean id="activeJobs" class="com.deshpande.Job" scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Job Listings</title>
        <link href="styles/main.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h2>Listings</h2>
            <div class="pagination">
                <c:forEach var="i" begin="1" end="${maxPages}">
                    <a <c:if test="${currentPage == i}">class="active"</c:if>
                                                        href="<c:url value="/jobs">
                                                            <c:param name="page"
                                                                     value="${i}"/>
                                                        </c:url>">${i}</a>
                </c:forEach>
            </div>
            <div class="jobs">
                <c:forEach items="${activeJobs}" var="job" begin="${begin}" end="${end}">
                    <div class="job">
                        <p><c:out value="${fn:escapeXml(job.title)}"/><br>
                            <c:out value="${fn:escapeXml(job.city)}"/>&nbsp;
                            <c:out value="${fn:escapeXml(job.department)}" /></p>

                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>

