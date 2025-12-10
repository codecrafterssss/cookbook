<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- only if using fn:* --%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>${recipe.title} - CookBook</title>
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
<main>
<h1>${recipe.title}</h1>
<p><strong>Chef:</strong> <c:out value="${recipe.chef.name}"/></p>
<p><strong>Cook time:</strong> <c:out value="${recipe.cookTime}"/> minutes</p>
<p><strong>Calories:</strong> <c:out value="${recipe.totalCalories}"/></p>
<h3>Ingredients</h3>
<ul>
<c:forEach items="${recipe.recipeIngredients}" var="ri">
<li>${ri.ingredient.name}</li>
</c:forEach>
</ul>
<h3>Instructions</h3>
<pre>${recipe.instructions}</pre>
<p><a href="${pageContext.request.contextPath}/recipes">Back to recipes</a></p>
</main>
</body>
</html>