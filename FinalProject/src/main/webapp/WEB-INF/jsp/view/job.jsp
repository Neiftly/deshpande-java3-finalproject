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
                <h1>Listing</h1>
                <Span name="successMessage">${sessionScope.successMessage}</Span>
                <div class="column one">

                    <h2>${fn:escapeXml(job.title)}</h2>
                <p>Location:&emsp;${fn:escapeXml(job.city)}, ${fn:escapeXml(job.state)}</p>
                <p>Status:&emsp;<c:choose>
                        <c:when test = "${job.fullTime}">
                            Full Time
                        </c:when>
                        <c:otherwise>
                            Part-Time
                        </c:otherwise>
                    </c:choose>
                </p>
                <p>Department:&emsp; ${fn:escapeXml(job.department)}</p>
                <p>Experience:&emsp; ${fn:escapeXml(job.experience)}</p>
                <c:set var="salary" value="${job.salary}"/>
                <p>Salary:&emsp;<fmt:formatNumber value = "${salary}" 
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
                <h2>Apply:</h2>
                <form method="POST" action="<c:url value="/applications" />" enctype="multipart/form-data">
                    <label for="firstName">First Name:</label>
                    <input type="text" name="firstName" id="firstName" value="${fn:escapeXml(application.firstName)}" placeholder="J"/>
                    <span name="fNameError">${application.firstNameError}</span><br><br>
                    <label for="lastName">Last Name:</label>
                    <input type="text" name="lastName" id="lastName" value="${fn:escapeXml(application.lastName)}" placeholder="Doe" />
                    <span name="lNameError">${application.lastNameError}</span><br><br>
                    <label for="email">Email:</label>
                    <input type="email" name="email" id="email" value="${fn:escapeXml(application.email)}" placeholder="J.Doe@email.com"/>
                    <span name="emailError">${application.emailError}</span><br><br>
                    <label for="phone">Phone:</label>
                    <input type="tel" name="phone" id="phone" 
                           pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" value="${fn:escapeXml(application.phone)}" placeholder="123-456-7890" />
                    <span name="phoneError">${application.phoneError}</span><br><br>
                    <label for="resumeUpload">Resume: </label>
                    <input type="file" name="resumeUpload" id="resumeUpload" value="${fn:escapeXml(application.resumeUpload)}" />
                    <span name="resumeUploadError">${application.resumeError}</span><br><br>
                    <label for="desiredSalary">Desired Salary:</label>
                    <input type="number" min="1" step="any" name="desiredSalary" id="desiredSalary" value="${fn:escapeXml(application.desiredSalary)}" />
                    <span name="desiredsalaryError">${application.salaryError}</span><br><br>
                    <label for="earliestStartDate">Earliest Start Date:</label>
                    <input type="date" name="earliestStartDate" id="earliestStartDate" value="${fn:escapeXml(application.earliestStartDate)}" />
                    <span name="earliestStartDateError">${fn:escapeXml(application.startDateError)}</span><br><br>
                    <input type="submit" value="Apply" />
                    <input type="reset">
                </form>
            </div>
        </div>
    </body>
</html>
