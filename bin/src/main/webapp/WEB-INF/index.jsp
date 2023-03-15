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
    <title>Project Manager</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
    <h1 class="text-center align-items-center justify-content-center text-primary">Project Manager</h1>
    <p class="align-items-center justify-content-center text-center">A place for teams to manage products</p>
    <div class="d-flex align-items-center justify-content-center">
        <div>
            <h2>Register</h2>
            <%--@elvariable id="newUser" type=""--%>
            <form:form method="post" action="/register" modelAttribute="newUser">
                <div>
                    <form:label path="firstName">First Name</form:label>
                    <form:errors path="firstName"/>
                    <form:input path="firstName"/>
                </div>
                <div>
                    <form:label path="lastName">Last Name</form:label>
                    <form:errors path="lastName"/>
                    <form:input path="lastName"/>
                </div>
                <div>
                    <form:label path="email">Email</form:label>
                    <form:errors path="email"/>
                    <form:input path="email"/>
                </div>
                <div>
                    <form:label path="password">Password:</form:label>
                    <form:errors path="password"/>
                    <form:input path="password" type="password"/>
                </div>
                <div>
                    <form:label path="confirm">Confirm PW:</form:label>
                    <form:errors path="confirm"/>
                    <form:input path="confirm" type="password"/>
                </div>
                <input type="submit" value="Submit">
            </form:form>
        </div>
        <div>
            <h2>Login</h2>
            <%--@elvariable id="newLogin" type=""--%>
            <form:form modelAttribute="newLogin" action="/login" method="post">
                <div>
                    <form:label path="email">Email:</form:label>
                    <form:errors path="email"/>
                    <form:input path="email"/>
                </div>
                <div>
                <form:label path="password">Password</form:label>
                <form:errors path="password"/>
                <form:input path="password" type="password"/>
                </div>
                <input type="submit" value="Submit">
            </form:form>
        </div>
    </div>
</body>
</html>