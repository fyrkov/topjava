<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.jsp">Home</a></h2>
    <form method="post" action="meals">
        <input type="hidden" name="action" value="filter">
        <ul>
            <c:set var="ld1" value='${requestScope["ld1"]}' />
            <c:set var="ld2" value='${requestScope["ld2"]}' />
            <c:set var="lt1" value='${requestScope["lt1"]}' />
            <c:set var="lt2" value='${requestScope["lt2"]}' />
            <li>DateFrom:&nbsp;<input type="date" value="${ld1}" name="dateFrom">&nbsp;</li>
            <li>TimeFrom:&nbsp;<input type="time" value="${lt1}" name="timeFrom"></li>
            <li>DateTo:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="date" value="${ld2}" name="dateTo">&nbsp;</li>
            <li>TimeTo:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="time" value="${lt2}" name="timeTo"><br></li>
        </ul>
        <button type="submit">filter</button>
    </form>

    <h2>Meal list</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>