<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- only if using fn:* --%>
<!doctype html>
<html>
<head><meta charset="utf-8"/><title>Add Recipe</title><link rel="stylesheet" href="../css/style.css"/></head>
<body>
<h2>Add Recipe</h2>
<form method="post" action="${pageContext.request.contextPath}/admin/recipes">
<input type="hidden" name="action" value="create" />
<label>Title<br/><input type="text" name="title" required/></label><br/>
<label>Instructions<br/><textarea name="instructions" rows="6"></textarea></label><br/>
<label>Cook Time (min)<br/><input type="number" name="cookTime"/></label><br/>
<label>Total Calories<br/><input type="number" name="totalCalories"/></label><br/>
<label>Chef<br/>
<select name="chefId">
<option value="">-- select chef --</option>
<c:forEach items="${chefs}" var="c">
<option value="${c.chefId}">${c.name}</option>
</c:forEach>
</select>
</label><br/>
<label>Ingredients (comma separated)<br/><input type="text" name="ingredients"/></label><br/>
<button type="submit">Create</button>
</form>
<p><a href="${pageContext.request.contextPath}/admin/recipes">Back to list</a></p>
</body>
</html>