<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 12.12.2016
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Список еды</h2>
<table style="height: 86px;" border="1" width="608" cellspacing="1" cellpadding="10">
    <tbody>
    <tr>
        <th>Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
        <% List<MealWithExceed> list = (List<MealWithExceed>) request.getAttribute("mealsMemorylist");
        for (MealWithExceed meal : list) { %>
    <tr>
        <% if (meal.isExceed()) {%> <tr style="background-color:#ffcccc;"> <% } else {%> <tr style="background-color:#e6ffe6;"> <%} %>
        <td><%=meal.getDate()%> &nbsp;<%=meal.getTime()%></td>
        <td><%=meal.getDescription()%> </td>
        <td><%=meal.getCalories()%> </td>
    </tr>
        <%}
        %>
    </tbody>
</table>
<p>&nbsp;</p>
</body>
</html>
