<%@ page import="java.util.*, com.codegnan.model.Ingredient" %>

<!DOCTYPE html>
<html>
<head>
<title>Admin - Ingredients</title>
</head>
<body>

<h2>ADMIN - INGREDIENTS</h2>

<h3>Add New Ingredient</h3>
<form action="../admin/ingredients" method="post">
    <input type="hidden" name="action" value="add">
    Name:
    <input type="text" name="name" required>
    <button type="submit">Add</button>
</form>

<hr>

<h3>Existing Ingredients</h3>

<%
List<Ingredient> list = (List<Ingredient>) request.getAttribute("ingredients");
if (list != null) {
    for (Ingredient ing : list) {
%>
        <form action="../admin/ingredients" method="post">
            ID: <%= ing.getIngredientId() %>
            <input type="hidden" name="id" value="<%= ing.getIngredientId() %>">

            <input type="text" name="name" value="<%= ing.getName() %>">

            <button type="submit" name="action" value="update">Update</button>
            <button type="submit" name="action" value="delete">Delete</button>
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
