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
    <title>Edit Project</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
    <h1>Edit Project</h1>
    <%--@elvariable id="project" type=""--%>
    <form:form modelAttribute="project" method="put" action="/projects/edit/${project.id}">
        <div>
            <form:label path="title">Project Title: </form:label>
            <form:errors path="title"/>
            <form:input path="title"/>
        </div>
        <div>
            <form:label path="description">Project Description: </form:label>
            <form:errors path="description"/>
            <form:textarea path="description"/>
        </div>
        <div>
            <form:label path="dueDate">Due Date: </form:label>
            <form:errors path="dueDate"/>
            <form:input type="date" path="dueDate"/>
        </div>
        <input type="submit" value="Submit">
    </form:form>
</body>
</html>