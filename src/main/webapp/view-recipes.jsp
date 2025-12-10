<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- only if using fn:* --%>
<!doctype html>
<html>
<head><meta charset="utf-8"/><title>Admin - Recipes</title><link rel="stylesheet" href="../css/style.css"/></head>
<body>
<h2>Recipes</h2>
<p><a href="${pageContext.request.contextPath}/admin/recipes?action=add">Add Recipe</a></p>
<table>
<thead><tr><th>Title</th><th>Chef</th><th>Calories</th><th>Actions</th></tr></thead>
<tbody>
<c:forEach items="${recipes}" var="r">
<tr>
<td>${r.title}</td>
<td><c:out value="${r.chefId}"/></td>
<td><c:out value="${r.totalCalories}"/></td>
<td>
<a href="${pageContext.request.contextPath}/admin/recipes?action=edit&id=${r.recipeId}">Edit</a>
|
<a href="${pageContext.request.contextPath}/admin/recipes?action=delete&id=${r.recipeId}" onclick="return confirm('Delete?')">Delete</a>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</body>
</html>