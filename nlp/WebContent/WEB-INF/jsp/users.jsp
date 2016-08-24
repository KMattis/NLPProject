<%@page import="de.microstep.JSPUtils"%>
<html>
<head>
	<title>${page_title}</title>
</head>
<body>
	<!-- Page menu -->
	<jsp:include page="header.jsp"/>

	<!-- Page content -->
	<div>
		${page_data}
	</div>
	
	<table>
		<tr><td>Username</td><td>Click to delete user</td></tr>
		<% for(String[] data : JSPUtils.getAllUsers()){ %>
		
		<tr><td> <%= data[0] %> </td><td> 
 		<%	if(!Boolean.parseBoolean(data[2])) { %>
		<a href="users?remove=<%= data[0] %>">Remove</a> 
		<%	}%>
		</td></tr>
		
		<%} %>
	</table>
</body>
</html>