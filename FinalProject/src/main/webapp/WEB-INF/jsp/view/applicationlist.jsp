<%-- 
    Document   : applicationlist
    Created on : Apr 30, 2021, 4:00:17 PM
    Author     : Vinayak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <t:head htmlTitle="Applications"></t:head>
        <body>
            <t:nav></t:nav>
            
            <div class="container">
                <h2>Applications</h2>

                <div class="pagination">
                <c:forEach var="i" begin="1" end="${maxPages}">
                    <a <c:if test="${currentPage == i}">class="active"</c:if>
                                                        href="<c:url value="/applications">
                                                            <c:param name="page"
                                                                     value="${i}"/>
                                                        </c:url>">${i}</a>
                </c:forEach>
            </div>
            <div class="applications">
                <c:forEach items="${activeApps}" var="application" begin="${begin}" end="${end}">
                    <div class="application">
                        <a class="title" href="<c:url value="/applications">
                               <c:param name="action" value="view" />
                               <c:param name="id" value="${application.id}" />
                           </c:url>"><c:out value="${fn:escapeXml(application.jobTitle)}"/></a>
                        <br><c:out  value="${fn:escapeXml(application.firstName)}  ${fn:escapeXml(application.lastName)}"/>
                        &nbsp;<c:out  value="${fn:escapeXml(application.email)}" /><br><br>

                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
