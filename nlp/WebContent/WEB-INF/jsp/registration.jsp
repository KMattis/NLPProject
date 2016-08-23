<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>NLP TEST PROJECT</title>
	<link rel="stylesheet" href="css/menu.css"></link>
</head>
<body>
	<jsp:include page="header.jspx"></jsp:include>
	
	<!--${header_menu}-->
	<div style="align:center">
		${message}
	</div>
	<p/>
	<br/>
	<div style="align:center">
		<form method='POST'>
			Name:
			<input type='text' name='name' required/><br>
			Password:
			<input type='password' name='pass' required/><br/>
			Admin:
			<input type='checkbox' name='admin'/><br/>
			<input type='submit' value='Register!'/>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</div>
</body>
</html>