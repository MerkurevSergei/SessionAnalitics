<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS, JavaScript dependencies -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css"
          integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js"
            integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/"
            crossorigin="anonymous"></script>


    <title>Анализ сессий</title>
</head>
<body>
<div class="container overflow-hidden">
    <div class="row mt-1">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Главная</a>
            </li>
            <li class="nav-item">
                <c:choose>
                    <c:when test="${empty user}">
                        <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link" href="<%=request.getContextPath()%>/auth.do?action=quit"> <c:out
                                value="${user.name}"/> | Выйти</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>
    <div class="row mt-2">
        <div class="col-auto">
            <form method="post" class="card card-body">
                <div class="form-group card-title">
                    <label for="startDate">Период:</label><br>
                    <input type="date" id="startDate" name="startDate" value=${startDate}>
                </div>
                <button type="submit" class="btn btn-primary btn-sm card-text">Отправить</button>
            </form>
            <div class="card card-body mt-3">
                <h6>Легенда:</h6>
                <ul class="list-group" style="font-size: 0.7rem">
                    <li class="list-group-item bg-success">
                        Подтверждено
                    </li>
                    <li class="list-group-item bg-warning text-danger">
                        Не подтверждено
                    </li>
                    <li class="list-group-item bg-danger text-white-50">
                        Выключен ПК
                    </li>
                    <li class="list-group-item bg-primary text-white-50">
                        Не подключен удаленно<br>
                        Предполагается работа в офисе
                    </li>
                    <li class="list-group-item bg-secondary">
                        Технический сбой
                    </li>
                </ul>
            </div>
        </div>
        <div class="col">
            <table class="table table-bordered table-hover table-sm">
                <thead>
                <th scope="col">Сессии</th>
                </thead>
                <tbody>
                <c:forEach items="${journal}" var="entry">
                    <tr>
                        <td>
                            <c:out value="${entry.key}"/>
                        </td>
                        <c:forEach items="${entry.value}" var="event">
                            <c:choose>
                                <c:when test="${event.status == 1}">
                                    <td class="bg-success text-center">
                                        <fmt:parseDate value="${ event.date }" pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDateTime" type="time"/>
                                        <fmt:formatDate pattern="HH:mm" value="${ parsedDateTime }"/>
                                    </td>
                                </c:when>
                                <c:when test="${event.status == 0}">
                                    <td class="bg-warning text-danger text-center">
                                        <fmt:parseDate value="${ event.date }" pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDateTime" type="time"/>
                                        <fmt:formatDate pattern="HH:mm" value="${ parsedDateTime }"/>
                                    </td>
                                </c:when>
                                <c:when test="${event.status == -1}">
                                    <td class="bg-secondary text-center">
                                        <fmt:parseDate value="${ event.date }" pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDateTime" type="time"/>
                                        <fmt:formatDate pattern="HH:mm" value="${ parsedDateTime }"/>
                                    </td>
                                </c:when>
                                <c:when test="${event.status == -2}">
                                    <td class="bg-danger text-white-50 text-center">
                                        <fmt:parseDate value="${ event.date }" pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDateTime" type="time"/>
                                        <fmt:formatDate pattern="HH:mm" value="${ parsedDateTime }"/>
                                    </td>
                                </c:when>
                                <c:when test="${event.status == -3}">
                                    <td class="bg-primary text-center text-white-50">
                                        <fmt:parseDate value="${ event.date }" pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDateTime" type="time"/>
                                        <fmt:formatDate pattern="HH:mm" value="${ parsedDateTime }"/>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td class="text-center">
                                        <fmt:parseDate value="${ event.date }" pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDateTime" type="time"/>
                                        <fmt:formatDate pattern="HH:mm" value="${ parsedDateTime }"/>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>