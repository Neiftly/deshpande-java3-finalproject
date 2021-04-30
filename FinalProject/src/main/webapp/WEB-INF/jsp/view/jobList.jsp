<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- 
    Document   : jobList
    Created on : Apr 13, 2021, 7:46:06 PM
    Author     : Vinayak
--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Job Listings</title>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
        <link href="styles/main.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            
            <nav>
                <a href="<c:url value="/jobs"/>">View Jobs</a>
                <a href="<c:url value="/applications"/>">Applications</a>
            </nav>
            <h2>Job Listings</h2>
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
                        <a class="title" href="<c:url value="/jobs">
                               <c:param name="action" value="view" />
                               <c:param name="id" value="${job.id}" />
                           </c:url>"><c:out value="${fn:escapeXml(job.title)}"/></a>
                        <br><c:out  value="${fn:escapeXml(job.city)}, ${fn:escapeXml(job.state)}"/>
                        &nbsp;<c:out  value="${fn:escapeXml(job.department)}" /><br><br>

                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>

