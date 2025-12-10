<h2>Ingredients</h2>
<form method="post" action="${pageContext.request.contextPath}/admin/ingredients">
    <label>Name<br/><input type="text" name="name" required/></label>
    <button type="submit">Add</button>
</form>

<table>
    <c:forEach items="${ingredients}" var="ing">
        <tr>
            <td>${ing.name}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/ingredients?action=delete&id=${ing.ingredientId}"
                   onclick="return confirm('Delete?')">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
