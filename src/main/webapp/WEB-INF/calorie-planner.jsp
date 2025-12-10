<form action="calorie-planner" method="post">
    <h2>Calorie Based Meal Planner</h2>

    Max Calories:
    <input type="number" name="maxCalories" required>

    <button type="submit">Search</button>
</form>

<hr>

<%@ page import="java.util.*, com.codegnan.model.Recipe" %>

<%
List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
if (recipes != null) {
    for (Recipe r : recipes) {
%>
        <h3><%= r.getTitle() %></h3>
        <p><%= r.getTotalCalories() %> calories</p>
        <a href="recipe-details?id=<%= r.getRecipeId() %>">View</a>
        <hr>
<%
    }
}
%>
