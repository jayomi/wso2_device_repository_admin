<%@page import="com.devicemgt.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WSO2 Device Repository</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
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
			<li><a herf="about (copy).html"><span><span>About</span></span></a></li>
			<li><a>Device</a>
				<ul>
					<li><a href="add_device.jsp">Add Devices</a></li>
					<li><a href="get_device.jsp">Edit Details</a></li>
					<li><a href="get_device.jsp">Search</a></li>
					<li><a href="get_device.jsp">Remove Device</a></li>
				</ul></li>
			<li><a href="index.jsp">Activity</a>
				<ul>
					<li><a href="add_transaction.jsp">Lend</a></li>
					<li><a href="register.jsp">Return</a></li>
					<li><a href="get_transaction.jsp">View Activities</a></li>
					<li><a href="index.jsp">Update Activity</a></li>
					<li><a href="register.jsp">Delete Activity</a></li>
				</ul></li>
			<li><a href="index.jsp">Administration</a>
				<ul>
					<li><a href="index.jsp">My Information</a></li>
					<li><a href="register.jsp">Add User</a></li>
					<li><a href="index.jsp">Delete User</a></li>
					<li><a href="register.jsp">Edit User</a></li>
				</ul></li>

		</ul>
		</nav>

		<div id="frame">
			<div id="content">

				
				



					<%
			String actionType = "addTransaction";
			session.setAttribute("actionType", actionType);
		%>

		<h2 align="center">Add New Transaction</h2>
		<form action="TransactionController" method="get">
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
			<center>
			<table>
<tr height="60"></tr>

				<tbody >
					
					<tr>
						<td class="unandpwd">*  Device Name :</td>
						<td><input type="text" name="deviceId"
							placeholder="Enter Device Name" /></td>
					</tr>
					
					<tr>
						<td class="unandpwd">*  User Name:</td>
						<td><input type="text" name="userId"
							placeholder="Select User Name" /></td>
					</tr>
					
					<tr>
						<td class="unandpwd">*  Status :</td>
						<td><input type="text" name="transactionStatusId"
							placeholder="Enter Status" /></td>
					</tr>
					
					<tr>
						<td class="unandpwd">Transaction Date :</td>
						<td><input type="text" name="transactionDate"
							placeholder="Enter Transaction Date" /></td>
					</tr>
					
					<tr>
						<td class="unandpwd">Due Date:</td>
						<td><input type="text" name="dueDate"
							placeholder="Enter Due Date" /></td>
					</tr>					

					<tr>
						<td></td>
						<td><input type="submit" value="Add Device" /></td>
					</tr>
					
					<tr height="60"></tr>
				</tbody>
			</table></center>
		</form>

					

			</div>
		</div>

		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>