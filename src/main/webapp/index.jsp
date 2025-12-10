<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- only if using fn:* --%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>CookBook</title>
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
<header>
<h1>CookBook</h1>
<nav>
<a href="${pageContext.request.contextPath}/recipes">Recipes</a>
<a href="${pageContext.request.contextPath}/calorie-planner">Calorie Planner</a>
<a href="${pageContext.request.contextPath}/login">Login</a>
</nav>
</header>
<main>
<section>
<h2>Welcome to CookBook</h2>
<p>Find recipes by ingredients, plan meals by calories, or explore recipes from top chefs.</p>
<p><a class="btn" href="${pageContext.request.contextPath}/recipes">Browse Recipes</a></p>
</section>
</main>
<footer>
<p>&copy; 2025 CookBook</p>
</footer>
<script src="js/app.js"></script>
</body>
</html>