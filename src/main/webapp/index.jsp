<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Java Enterprise (Topjava)</title>
</head>
<body>
<h3>Проект <a href="https://github.com/JavaWebinar/topjava09" target="_blank">Java Enterprise (Topjava)</a></h3>

Select user:<br>
<form action="users" method="post">
        <select name="User">
            <option value="1" <c:if test="${param.User == '1'}"> selected </c:if>>admin</option>
            <option value="2" <c:if test="${param.User == '2'}"> selected </c:if>>user</option>
    </select>
    <p><input type="submit" value="Выбрать"></p>
</form>
${param.User == '2' ? 'current role = user' : 'current role = admin'}

<ul>
    <li><a href="users">User List</a></li>
    <li><a href="meals">Meal List</a></li>
</ul>
</body>
</html>
