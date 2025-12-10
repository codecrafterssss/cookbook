<%@ page import="com.codegnan.model.Recipe" %>

<%
Recipe recipe = (Recipe) request.getAttribute("recipe");
%>

<h2><%= recipe.getTitle() %></h2>
<p><strong>Cook Time:</strong> <%= recipe.getCookTime() %> mins</p>
<p><strong>Calories:</strong> <%= recipe.getTotalCalories() %></p>
<p><strong>Instructions:</strong></p>
<p><%= recipe.getInstructions() %></p>

<a href="index.jsp">Back</a>
