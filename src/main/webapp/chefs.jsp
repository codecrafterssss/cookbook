<h2>Chefs</h2>
<form method="post" action="${pageContext.request.contextPath}/admin/chefs">
    <label>Name<br/><input type="text" name="name" required/></label>
    <button type="submit">Add</button>
</form>

<table>
    <c:forEach items="${chefs}" var="c">
        <tr>
            <td>${c.name}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/chefs?action=delete&id=${c.chefId}"
                   onclick="return confirm('Delete?')">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
