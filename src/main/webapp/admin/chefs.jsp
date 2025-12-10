<%@ page import="java.util.*, com.codegnan.model.Chef" %>

<!DOCTYPE html>
<html>
<head>
<title>Admin - Chefs</title>
</head>
<body>

<h2>ADMIN - CHEFS</h2>

<h3>Add New Chef</h3>
<form action="../admin/chefs" method="post">
    <input type="hidden" name="action" value="add">
    Name:
    <input type="text" name="name" required>
    <button type="submit">Add</button>
</form>

<hr>

<h3>Existing Chefs</h3>

<%
List<Chef> list = (List<Chef>) request.getAttribute("chefs");
if (list != null) {
    for (Chef c : list) {
%>
        <form action="../admin/chefs" method="post">
            ID: <%= c.getChefId() %>
            <input type="hidden" name="id" value="<%= c.getChefId() %>">

            <input type="text" name="name" value="<%= c.getName() %>">

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
