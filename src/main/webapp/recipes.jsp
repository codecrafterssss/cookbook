<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- only if using fn:* --%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>Recipes - CookBook</title>
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
<header>
<h1>Recipes</h1>
<a href="${pageContext.request.contextPath}/">Home</a>
</header>
<main>
<section class="search">
<h3>Find recipes by ingredients</h3>
<form method="post" action="${pageContext.request.contextPath}/recipes">
<input type="text" name="ingredients" placeholder="eg: egg, tomato, cheese" value="${searchIngredients}" />
<button type="submit">Search</button>
</form>
</section>


<section class="list">
<c:if test="${empty recipes}">
<p>No recipes found.</p>
</c:if>
<c:forEach items="${recipes}" var="r">
<article class="recipe-card">
<h4><a href="${pageContext.request.contextPath}/recipe-details?id=${r.recipeId}">${r.title}</a></h4>
<p>Cook time: <c:out value="${r.cookTime}"/> minutes</p>
<p>Calories: <c:out value="${r.totalCalories}"/></p>
</article>
</c:forEach>
</section>
</main>
</body>
</html>