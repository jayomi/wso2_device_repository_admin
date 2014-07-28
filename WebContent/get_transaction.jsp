<%@page import="com.devicemgt.util.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.devicemgt.model.Transaction"%>
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
        	<%
		String actionType = "getTransaction";
		session.setAttribute("actionType", actionType);
	%>
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
			<li><a href="about (copy).html"><span><span>About</span></span></a></li>
			<li><a>Device</a>
				<ul>
					<li><a href="add_device.jsp">Add Devices</a></li>
					<li><a href="get_device.jsp">Edit Details</a></li>
					<li><a href="get_device.jsp">Search</a></li>
					<li><a href="get_device.jsp">Remove Device</a></li>
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
					<li><a href="register.jsp">Add User</a></li>
					<li><a href="index.jsp">Delete User</a></li>
					<li><a href="register.jsp">Edit User</a></li>
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
		<div id="frame">
			<div id="content">
				<form action="TransactionController" method="get">
					<table width="900" height="80" border="0" cellspacing="1">
						<tr height="60"></tr>



						<tr>

							<td width="250"></td>
							<td width="600">
								<h3 align="left">Activities</h3> <br /> <input type="text"
								name="transactionId" placeholder="Enter Transaction Name" /> <br /> <input
								type="submit" value="Get All Transaction" name="getTransaction" /> <br />
								<input type="submit" value="Search" name="getSearch" /> <br />


							</td>
							

					<%-- 		<td><select name="transactionId">
							<%
			LinkedList<Transaction> transactionList = (LinkedList<Transaction>) session
					.getAttribute(BackendConstants.LOGIN);
		%>
									
										
										<%
							for (int y = 0; y < transactionList.size(); y++) {
						%>
							<option><%=transactionList.get(y).getTransactionId()%></option>
					
						<%
							}
						%>	
							</select></td> --%>
							<td width="20">
						</tr>
						<tr height="60"></tr>
					</table>

				</form>

			</div>
		</div>

		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>