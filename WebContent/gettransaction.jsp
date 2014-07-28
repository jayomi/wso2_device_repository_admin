<%@page import="com.devicemgt.util.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.devicemgt.model.Transaction"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

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
		<%
			LinkedList<Transaction> transactionList = (LinkedList<Transaction>)session.getAttribute(BackendConstants.LOGIN);
		%>

		<div id="frame">
			<div id="content">

				<form action="TransactionController" method="get">
					<table width="900" height="80" border="1" cellspacing="1">
						<tr height="60"></tr>




						<tr>
							<th>Activity Id</th>
							<th>Device</th>
							<th>User</th>
							<th>Status</th>
							<th>Transaction Date</th>
							<th>Due Date</th>
							<th>Return Date</th>

						</tr>
						<%
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						
							for (int y = 0; y < transactionList.size(); y++) {
								
								String dueDate="";
								try
								{
								if(dateFormat.format(transactionList.get(y).getDueDate())!= null)
								{
									dueDate = dateFormat.format(transactionList.get(y).getDueDate());
								}
								}
								catch(Exception e)
								{}
								
								String transactionDate="";
								try
								{
								if(dateFormat.format(transactionList.get(y).getTransactionDate())!= null)
								{
									transactionDate = dateFormat.format(transactionList.get(y).getTransactionDate());
								}
								}
								catch(Exception e)
								{}
								
								String returnDate="";
								try
								{
								if(dateFormat.format(transactionList.get(y).getReturnDate())!= null)
								{
									returnDate = dateFormat.format(transactionList.get(y).getReturnDate());
								}
								}
								catch(Exception e)
								{}
						%>
						<tr>
							<td align="center"><%=transactionList.get(y).getTransactionId()%></td>
							<td align="center"><%=transactionList.get(y).getDeviceId()%></td>
							<td align="center"><%=transactionList.get(y).getUserId()%></td>
							<td align="center"><%=transactionList.get(y).getTransactionStatusId()%></td>
							<td align="center"><%=transactionDate%></td>
							<td align="center"><%=dueDate%></td>
							<td align="center"><%=returnDate%></td>
							
						</tr>
						<%
							}
						%>


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