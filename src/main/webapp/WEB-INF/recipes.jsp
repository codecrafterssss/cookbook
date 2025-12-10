<%@ page import="java.util.*, com.codegnan.model.Recipe" %>

<h2>Recommended Recipes</h2>

<%
List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
if (recipes != null) {
    for (Recipe r : recipes) {
%>
        <div>
            <h3><%= r.getTitle() %></h3>
            <p>Cook Time: <%= r.getCookTime() %> mins</p>
            <p>Calories: <%= r.getTotalCalories() %></p>

            <a href="recipe-details?id=<%= r.getRecipeId() %>">View Details</a>
        </div>
        <hr>
<%
    }
} else {
%>
    <p>No recipes found</p>
<%
}
%>
