<%@page import="com.devicemgt.util.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.devicemgt.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <meta http-equiv=”refresh” content=”5" /> -->
<title>WSO2 Device Repository</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />

<script>
	function loadUsers() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("maindiv").innerHTML = xmlhttp.responseText;
			}
		}
		xmlhttp.open("GET", "UserController", true);
		xmlhttp.send();
	}
</script>

</head>
<body id="page1">


	<div id="maindiv">
		<table width="900" height="80" border="0" cellspacing="1">
			<tr>
				<td width="130"><img src="images/wso2-logo.png" width="125"
					height="50" /></td>
				<td width="600">
					<h1 align="left">
						<font id="headerfont"> Device Repository </font>
					</h1>
				</td>
			</tr>
		</table>

		<nav>
		<ul id="menu">
			<li><a href="index.jsp"><span><span>Home</span></span></a></li>
			<li><a href="about.jsp"><span><span>About</span></span></a></li>
			<li><a>Device</a>
				<ul>
					<li><a href="add_device.jsp">Add Devices</a></li>
					<li><a href="updateordelete_device.jsp">Edit Details</a></li>
					<li><a href="get_device.jsp">Search</a></li>
					<li><a href="updateordelete_device.jsp">Remove Device</a></li>
				</ul></li>
			<li><a href="index.jsp">Activity</a>
				<ul>
					<li><a href="index.jsp">Lend</a></li>
					<li><a href="register.jsp">Return</a></li>
					<li><a href="index.jsp">Update Activity</a></li>
					<li><a href="register.jsp">Delete Activity</a></li>
				</ul></li>
			<li><a href="index.jsp">Administration</a>
				<ul>
					<li><a href="index.jsp">My Information</a></li>
					<li><a href="user_register.jsp">Add User</a></li>
					<li><a href="get_user.jsp">Delete User</a></li>
					<li><a href="get_user.jsp">Edit User</a></li>
				</ul></li>

		</ul>
		</nav>

		<center style="Background-color: #ccff00;">
			<b><font color="red"> <%
 	String errorMessage = (String) request
      			.getAttribute(BackendConstants.ERROR_MESSAGE);
      	if (errorMessage != null) {
      		out.println("*" + errorMessage);
      	}
 %>
			</font></b>
		</center>



		<%
		/* String actionType2 = "editUser";
		session.setAttribute("actionType", actionType2); */
		
			LinkedList<User> deviceList = (LinkedList<User>) session
					.getAttribute("UserList");
		%>

		<div id="frame">
			<div id="content">
				<center>
					<h3>Delete Device</h3>
				</center>
				<form action="UserController" method="get">
					<table width="900" height="80" border="1" cellspacing="1">
						<tr height="60"></tr>
						<tr>
							<th>User Id</th>
							<th>User FirstName</th>
							<th>User LastName</th>
							<th>UserName</th>
							<th>Email</th>
							<th>Tele #</th>
							<!-- <th>Description</th> -->
							<th>Delete</th>
							<th>Edit</th>

						</tr>
						<%
							if (deviceList != null) {

												/* String actionType = "getUsersOnLoad";
												session.setAttribute("actionType", actionType); */
						%>
						<script type="text/javascript">
						loadUsers();
						</script>
						<%
							for (int y = 0; y < deviceList.size(); y++) {
						%>
						<tr>
							<td align="center"><%=deviceList.get(y).getUserId()%></td>
							<td align="center"><%=deviceList.get(y).getUserFname()%></td>
							<td align="center"><%=deviceList.get(y).getUserLname()%></td>
							<td align="center"><%=deviceList.get(y).getUsername()%></td>
							<td align="center"><%=deviceList.get(y).getEmail()%></td>
							<td align="center"><%=deviceList.get(y).getTelNo()%></td>
							<%-- <td align="center"><%=deviceList.get(y).getDescription()%></td> --%>
							<td align="center"><input type="radio" name="deleteUser"
								value="<%=deviceList.get(y).getUserId()%>"></td>
							<td align="center"><input type="radio" name="editUser"
								value="<%=deviceList.get(y).getUserId()%>"></td>

						</tr>
						<%
							}
											} else {

												String actionType = "getUsersOnLoad";
													session.setAttribute("actionType", actionType);
						%>

						<script type="text/javascript">
						loadUsers();
						</script>


						<%
							response.setIntHeader("Refresh", 5);

							}
						%>


						<tr height="60"></tr>
					</table>
					<input type="submit" name="deleteBtn" value="Delete" align="right">
					<input type="submit" name="editBtn" value="Edit" align="right">

				</form>
			</div>
		</div>

		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>