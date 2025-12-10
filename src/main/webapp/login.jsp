<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- only if using fn:* --%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>Login - CookBook</title>
<link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<h2>Login</h2>
<c:if test="${not empty error}">
<div class="error">${error}</div>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/login">
<label>Username<br/><input type="text" name="username" required></label><br/>
<label>Password<br/><input type="password" name="password" required></label><br/>
<button type="submit">Login</button>
</form>
<p>Don't have an account? <a href="#">Register (implement registration servlet)</a></p>
</div>
</body>
</html>