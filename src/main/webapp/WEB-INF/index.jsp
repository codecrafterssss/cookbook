<%@ page import="com.codegnan.model.User" %>

<%
User user = (User) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}
%>

<h2>Welcome, <%= user.getUsername() %> !</h2>

<a href="recipes.jsp">Ingredient-Based Recipe Recommender</a><br><br>
<a href="calorie-planner.jsp">Calorie Based Planner</a><br><br>

<% if(user.getRole().equals("ADMIN")) { %>
<a href="admin/view-recipes.jsp">Admin Dashboard</a><br><br>
<% } %>

<a href="logout">Logout</a>
