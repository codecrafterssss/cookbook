<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- only if using fn:* --%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>Calorie Planner - CookBook</title>
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
<main>
<h1>Calorie Planner</h1>
<c:if test="${not empty error}"><div class="error">${error}</div></c:if>
<form method="post" action="${pageContext.request.contextPath}/calorie-planner">
<label>Target calories: <input type="number" name="calories" value="${calories}" required></label>
<button type="submit">Find Recipes</button>
</form>


<section>
<c:if test="${not empty recipes}">
<h3>Recipes within ${calories} calories</h3>
<ul>
<c:forEach items="${recipes}" var="r">
<li><a href="${pageContext.request.contextPath}/recipe-details?id=${r.recipeId}">${r.title}</a> — ${r.totalCalories} cal</li>
</c:forEach>
</ul>
</c:if>
</section>
</main>
</body>
</html>