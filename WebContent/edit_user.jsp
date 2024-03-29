<%@page import="com.devicemgt.util.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.devicemgt.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WSO2 Device Repository</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />

</head>
<body id="page1">

	<%
		String username = null;
		String role = "user";

		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("user_name")) {
					username = cookie.getValue();
				} else if (cookie.getName().equals("user_role")) {
					role = cookie.getValue();
				}
			}
		}

		if (username != null) {
	%>
	<table height="100" width="100%" border="0" cellspacing="1"
		bgcolor="#474747">
		<tr>
			<td><font color="#fff">Welcome : <%=username%></font></td>
		</tr>
		<tr>
			<td width="160" />
			<td width="800"><img src="images/banner.png" width="600"
				height="80" /></td>
			<td width="130"><img src="images/wso2-logo.png" width="100"
				height="40" /></td>
		</tr>
	</table>
	<div id="maindiv">

		<nav>
		<ul id="menu">
			<li><a href="home.jsp"><span><span>Home</span></span></a></li>
			<li><a href="about.jsp"><span><span>About</span></span></a></li>
			<li><a>Device</a>
				<ul>
					<li><a href="add_device.jsp">Add Devices</a></li>
					<li><a href="updateordelete_device.jsp">Alter Details</a></li>
					<li><a href="get_device.jsp">View Device</a></li>
					<li><a href="add_devicetype.jsp">Add Device Type</a></li>
					<li><a href="getdevicetype.jsp">View Device Type</a></li>
				</ul></li>
			<li><a href="home.jsp">Activity</a>
				<ul>
					<li><a href="add_transaction.jsp">Lend</a></li>
					<li><a href="get_transaction.jsp">View Activity</a></li>
					<li><a href="updateordelete_transaction.jsp">Alter
							Activity</a></li>
				</ul></li>
			<li><a href="home.jsp">Administration</a>
				<ul>
					<li><a href="getMyProfile.jsp">My Information</a></li>
					<li><a href="user_register.jsp">Add User</a></li>
					<li><a href="get_user.jsp">Delete User</a></li>
					<li><a href="get_user.jsp">Edit User</a></li>
					<li><a href="add_transaction_status.jsp">Add Activity
							Status</a></li>
					<li><a href="get_transaction_status.jsp">View Activity
							Status</a></li>
					<li><a href="add_device_status.jsp">Add Device Status</a></li>
					<li><a href="get_device_status.jsp">View Device Status</a></li>

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
			LinkedList<User> deviceList = (LinkedList<User>) session
						.getAttribute("UserList");
		%>

		<div id="frame">
			<div id="content">

				<form action="UserController" method="post">
					<center>
						<table border="0" cellspacing="1">
							<tr height="60"></tr>

							<%
								if (deviceList != null) {

										for (int y = 0; y < deviceList.size(); y++) {
							%>
							<tr>
								<td>User Id</td>
								<td>: <input type="text" name="uID"
									value="<%=deviceList.get(y).getUserId()%>" /></td>
							</tr>
							<tr>
								<td>User FirstName</td>
								<td>: <input type="text" name="firstName"
									value="<%=deviceList.get(y).getUserFname()%>" /></td>
							</tr>
							<tr>
								<td>User LastName</td>
								<td>: <input type="text" name="lastName"
									value="<%=deviceList.get(y).getUserLname()%>" /></td>

							</tr>
							<tr>
								<td>UserName</td>
								<td>: <input type="text" name="userName"
									value="<%=deviceList.get(y).getUsername()%>" /></td>

							</tr>
							<tr>
								<td>Email</td>
								<td>: <input type="text" name="email"
									value="<%=deviceList.get(y).getEmail()%>" /></td>

							</tr>
							<tr>
								<td>Tele #</td>
								<td>: <input type="text" name="telephone"
									value="<%=deviceList.get(y).getTelNo()%>" /></td>

							</tr>
							<tr>
								<td>Role</td>
								<td>: <input type="text" name="role"
									value="<%=deviceList.get(y).getRole()%>" /></td>
							</tr>
							<tr>
								<td></td>
								<td align="right"><input type="submit" name="updateBtn"
									value="Update"></td>
							</tr>
							<%
								}
									}
							%>

							<tr height="60"></tr>
						</table>

					</center>
				</form>

				<%
					} else {
						String url = "/";
				%>
				<jsp:forward page="<%=url%>" />
				<%
					}
				%>
			</div>
		</div>

		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>