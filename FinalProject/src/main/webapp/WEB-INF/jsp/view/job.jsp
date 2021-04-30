<%-- 
    Document   : job
    Created on : Apr 13, 2021, 7:46:28 PM
    Author     : Vinayak
--%>
<%--@elvariable id="job" type="com.deshpande.Job"--%>
<%--@elvariable id="application" type="com.deshpande.Application"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <t:head htmlTitle="${job.title}"></t:head>
        <body>
            <t:nav></t:nav>
            <div class="container">
            
                <div class="column one">

                    <h3>${fn:escapeXml(job.title)}</h3>
                <p><span>Location:</span>${fn:escapeXml(job.city)}, ${fn:escapeXml(job.state)}</p>
                <p><span>Status:</span><c:choose>
                        <c:when test = "${job.fullTime}">
                            Full Time
                        </c:when>
                        <c:otherwise>
                            Part-Time
                        </c:otherwise>
                    </c:choose>
                </p>
                <p><span>Department:</span> ${fn:escapeXml(job.department)}</p>
                <p><span>Experience:</span> ${fn:escapeXml(job.experience)}</p>
                <c:set var="salary" value="${job.salary}"/>
                <p>Salary:<fmt:formatNumber value = "${salary}" 
                                  type = "currency"/>
                    <c:choose>
                        <c:when test = "${job.wageCategory == 'Salaried'}">
                            Per Year
                        </c:when>
                        <c:otherwise>
                            Per Hour
                        </c:otherwise>
                    </c:choose></p>
            </div>
            <div class="column two">
                <form method="POST" action="<c:url value="/applications" />" enctype="multipart/form-data">
                    <label for="firstName">First Name:</label>
                    <input type="text" name="firstName" id="firstName" /><br><br>
                    <label for="lastName">Last Name:</label>
                    <input type="text" name="lastName" id="lastName" /><br><br>
                    <label for="email">Email:</label>
                    <input type="email" name="email" id="email"/>
                    <label for="phone">Phone:</label>
                    <input type="tel" name="phone" id="phone" patter="[0-9]{3}-[0-9]{2}-[0-9]{3}">
                    <label for="resumeUpload">Resume: </label>
                    <input type="file" name="resumeUpload" id="resumeUpload"/>
                    <label for="desiredSalary">Desired Salary:</label>
                    <input type="number" min="1" step="any" class="desiredSalary" id="desiredSalary" />
                    <label for="earliestStartDate">Earliest Start Date:</label>
                    <input type="datetime-local" class="earliestStartDate" id="earliestStartDate"/>
                    <input type="submit" value="Apply" />
                    <input type="reset">
                </form>
            </div>
        </div>
    </body>
</html>
