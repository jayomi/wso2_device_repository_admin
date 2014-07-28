<%@page import="com.devicemgt.util.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.devicemgt.model.Device"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WSO2 Device Repository</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />

</head>
<body id="page1" onload="loadXMLDoc()">
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

			<%
				String actionType = "addDevice";
				session.setAttribute("actionType", actionType);
			%>
		</center>
		<div id="frame">
			<div id="content">
				<form action="UserController" method="get">
					<center>
						<h2>User Registration</h2>
						<table border="0" cellspacing="1">
							<tr>
						<td>First Name :</td>
						<td><input type="text" name="firstName"
							placeholder="Enter First Name" /></td>
					</tr>
					<tr>
						<td>Last Name :</td>
						<td><input type="text" name="lastName"
							placeholder="Enter Last Name" /></td>
					</tr>
					<tr>
						<td>User Name :</td>
						<td><input type="text" name="userName"
							placeholder="Enter User Name" /></td>
					</tr>

					<tr>
						<td>Password :</td>
						<td><input type="password" name="password"
							placeholder="Enter Password" /></td>
					</tr>
					<tr>
						<td>Confirm Password :</td>
						<td><input type="password" name="confirmPassword"
							placeholder="Re Enter Password" /></td>
					</tr>
					<tr>
						<td>Email :</td>
						<td><input type="text" name="email" placeholder="Enter Email" /></td>
					</tr>
					<tr>
						<td>Telephone :</td>
						<td><input type="text" name="telephone"
							placeholder="Enter Telephone #" maxlength="15" /></td>
					</tr>
					<tr>
						<td>Description :</td>
						<td><input type="text" name="description"
							placeholder="optional" /></td>
					</tr>


					<tr>
						<td></td>
						<td><input type="submit" value="Register" name="userRegister" /></td>
					</tr>
						</table>
					</center>
				</form>

			</div>
		</div>

		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>