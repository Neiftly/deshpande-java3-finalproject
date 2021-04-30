<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- 
    Document   : jobList
    Created on : Apr 13, 2021, 7:46:06 PM
    Author     : Vinayak
--%>

<!DOCTYPE html>
<html>
    <t:head htmlTitle="Job Listings"></t:head>
        <body>
            <t:nav></t:nav>
            
            <div class="container">
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

