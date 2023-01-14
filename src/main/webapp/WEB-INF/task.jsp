<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Props Page</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
    <div>
        <div>
            <h1>Project: <c:out value="${project.title}"/></h1>
            <p>Project Lead: <c:out value="${project.teamLeader.firstName}"/></p>
        </div>
        <a href="/dashboard">Back to Dashboard</a>
    </div>
    <hr>
    <%--@elvariable id="task" type=""--%>
    <form:form method="post" modelAttribute="task" action="/projects/${project.id}/tasks">
        <div>
            <form:label path="text">Add a task ticket for this team:</form:label>
            <form:errors path="text"/>
            <form:input path="text"/>
        </div>
<%--        <input type="hidden" value="${project.id}" path="project">--%>
<%--        <input type="hidden" value="${user.id}" path="user">--%>
        <input type="submit" value="Submit">
    </form:form>
    <c:forEach items="${project.tasks}" var="task">
        <div>
            <p>Added by <c:out value="${task.user.firstName}"/> at <c:out value="${task.createdAt}"/>:</p>
            <p><c:out value="${task.text}"/></p>
        </div>
    </c:forEach>
</body>
</html>