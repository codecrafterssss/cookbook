<%@ page import="java.util.*, com.codegnan.model.Recipe" %>

<!DOCTYPE html>
<html>
<head>
<title>Admin - Recipes</title>
</head>
<body>

<h2>ADMIN - RECIPES</h2>

<a href="add-recipe.jsp">➕ Add New Recipe</a>
<br><br>

<hr>

<%
List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
if (recipes != null) {
    for (Recipe r : recipes) {
%>
        <h3><%= r.getTitle() %></h3>
        Cook Time: <%= r.getCookTime() %> mins <br>
        Calories: <%= r.getTotalCalories() %> <br><br>

        <a href="update-recipe.jsp?id=<%= r.getRecipeId() %>">✏ Edit</a> |
        <form action="../admin/recipes" method="post" style="display:inline;">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="<%= r.getRecipeId() %>">
            <button type="submit">🗑 Delete</button>
        </form>
        <hr>
<%
    }
}
%>

<br><br>
<a href="../index.jsp">Back to Home</a>

</body>
</html>
