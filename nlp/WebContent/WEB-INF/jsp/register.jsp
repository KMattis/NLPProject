<html>
<head>
<title>${page_title}</title>
</head>
<body>
	<jsp:include page="header.jsp"/>
	
	<div style="text-align:center">
		<font color="red">
			${page_data}
		</font>
	</div>
	<p/>
	<br/>
	<div style="text-align:center">
		<form method='POST'>
			<table>
				<tr><td>Name:</td>    <td><input type='text' name='name' required/></td></tr>
				<tr><td>Password:</td><td><input type='password' name='pass' required/></td></tr>
				<tr><td>Admin:</td>   <td><input type='checkbox' name='admin'/></td></tr>
				<tr><td>				  <input type='submit' value='Register!'/></td></tr>
			</table>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</div>
</body>
</html>