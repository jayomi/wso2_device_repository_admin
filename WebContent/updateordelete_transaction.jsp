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
<!-- <meta http-equiv=”refresh” content=”5" /> -->
<title>WSO2 Device Repository</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />

<script>
	function loadDevices() {
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
		xmlhttp.open("GET", "TransactionController", true);
		xmlhttp.send();
	}
</script>

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
			LinkedList<Transaction> transactionList = (LinkedList<Transaction>) session
					.getAttribute("TransactionList");
		%>

		<div id="frame">
			<div id="content">
				<center>
					<h3>Alter Activity</h3>
				</center>
				<form action="TransactionController" method="get">
					<table width="900" height="80" class="tdata">
						<tr height="30"></tr>
						<tr>
							<th width="70">Activity Id</th>
							<th width="100">Device</th>
							<th width="100">User</th>
							<th width="100">Status</th>
							<th width="110">Transaction Date</th>
							<th width="110">Due Date</th>
							<th width="110">Return Date</th>
							<th width="110">Delete</th>
							<th width="110">Edit</th>
						</tr>
						<%
							if (transactionList != null) {

																String actionType = "getTransactionOnLoad";
																session.setAttribute("actionType", actionType);
						%>
						<script type="text/javascript">
							loadDevices();
						</script>
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
							<td align="center"><input type="radio"
								name="deleteTransaction"
								value="<%=transactionList.get(y).getTransactionId()%>"></td>
							<td align="center"><input type="radio"
								name="editTransaction"
								value="<%=transactionList.get(y).getTransactionId()%>"></td>

						</tr>
						<%
							}
															} else {

																String actionType = "getTransactionOnLoad";
																	session.setAttribute("actionType", actionType);
						%>

						<script type="text/javascript">
							loadDevices();
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