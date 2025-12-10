<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head><meta charset="utf-8"/><title>Register</title></head>
<body>
  <h2>Register</h2>
  <c:if test="${not empty error}"><div style="color:red">${error}</div></c:if>
  <form method="post" action="${pageContext.request.contextPath}/register">
    <label>Username<br/><input type="text" name="username" required/></label><br/>
    <label>Password<br/><input type="password" name="password" required/></label><br/>
    <button type="submit">Register</button>
  </form>
  <p><a href="${pageContext.request.contextPath}/login">Back to login</a></p>
</body>
</html>
