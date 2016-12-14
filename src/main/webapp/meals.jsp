<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 12.12.2016
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>Meals list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>

<form method="POST" action="meals" name="frmAddMeal">
    ID : <input
        type="text" readonly="readonly" name="id"
        value="<c:out value="${meal.getId()}" />"/> <br/>
    Дата и время: <input
        type="datetime-local" name="dateTime"
        value="<c:out value="${meal.getDateTime()}" />"/> <br/>
    Описание : <input
        type="text" name="description"
        value="<c:out value="${meal.getDescription()}" />"/> <br/>
    Калорийность : <input
        type="number" name="calories"
        value="<c:out value="${meal.getCalories()}" />"/> <br/>
    <input
            type="submit" name="action" value="submit"/>
</form>

<h2>Список еды</h2>
<table style="height: 86px;" border="1" width="608" cellspacing="1" cellpadding="10">
    <tbody>
    <tr>
        <th>ID</th>
        <th>Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>

    <c:forEach var="meal" items="${mealsMemorylist}">
        <tr>
        <c:if test="${meal.isExceed()}"> <tr style="background-color:#ffcccc;">
        </c:if>
        <c:if test="${!meal.isExceed()}"> <tr style="background-color:#e6ffe6;">
        </c:if>
        <td>${meal.getId()}</td>
        <td>${meal.getDate()} &nbsp;${meal.getTime()}</td>
        <td>${meal.getDescription()} </td>
        <td>${meal.getCalories()} </td>
        <td>
            <form action="meals">
                <input type="hidden" name="mealId" value="${meal.getId()}"/>
                <button type="submit" name="action" value="edit">Update</button>
            </form>
        </td>
        <td>
            <form action="meals" method="POST">
                <input type="hidden" name="mealId" value="${meal.getId()}"/>
                <button type="submit" name="action" value="delete">Delete</button>
            </form>
        </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p>&nbsp;</p>
</body>
</html>
