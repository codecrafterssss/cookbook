<%@ page import="com.codegnan.dao.RecipeDAO, com.codegnan.model.Recipe" %>

<%
int id = Integer.parseInt(request.getParameter("id"));
RecipeDAO dao = new RecipeDAO();
Recipe recipe = dao.getRecipeById(id);
%>

<!DOCTYPE html>
<html>
<head>
<title>Update Recipe</title>
</head>
<body>

<h2>UPDATE RECIPE</h2>

<form action="../admin/recipes" method="post">

    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%= recipe.getRecipeId() %>">

    Title: <br>
    <input type="text" name="title" value="<%= recipe.getTitle() %>" required><br><br>

    Instructions: <br>
    <textarea name="instructions" rows="6" cols="50" required><%= recipe.getInstructions() %></textarea><br><br>

    Cook Time (minutes): <br>
    <input type="number" name="cookTime" value="<%= recipe.getCookTime() %>" required><br><br>

    Total Calories: <br>
    <input type="number" name="totalCalories" value="<%= recipe.getTotalCalories() %>" required><br><br>

    Chef ID: <br>
    <input type="number" name="chefId" value="<%= recipe.getChefId() %>" required><br><br>

    <button type="submit">Update Recipe</button>
</form>

<br>
<a href="view-recipes.jsp">Back</a>

</body>
</html>
