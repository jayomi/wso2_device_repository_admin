package com.devicemgt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.dao.TransactionRepoDAO;
import com.devicemgt.dao.TransactionRepoDAOImpl;
import com.devicemgt.model.Transaction;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;

/**
 * Servlet implementation class DeviceController
 */
@WebServlet("/TransactionController")
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Transaction transaction;
	TransactionRepoDAO transactionRepoDAO;
	HttpAPICaller httpAPICaller;
	boolean isValidated = true;
	String transactionId;
    String deviceId;
    String userId;
    String transactionStatusId;
    Date transactionDate;
    Date returnDate;
    Date dueDate;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TransactionController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("Hello from Servlet\n\n");

		String actionType = (String) request.getSession(false).getAttribute(
				"actionType");
		
		
		
		RequestDispatcher requestDispatcher = null;
		
		 if (request.getParameter("getTransaction") != null) {
		        
			 actionType="getTransaction";

		    } else if (request.getParameter("getSearch") != null) {
		          actionType="getSearch";
		    }

		if (actionType.equals("addTransaction")) {

		    deviceId = request.getParameter("deviceId");
		    
		    userId = request.getParameter("userId");
	
		    transactionStatusId = request.getParameter("transactionStatusId");

		    try {
				transactionDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("transactionDate"));
		    } catch (Exception e) {
		    	transactionDate=null;
			}
		    
		    try {
				returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("returnDate"));
			} catch (Exception e) {				
				returnDate=null;
			}
		    
		    try {
				dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dueDate"));
			} catch (Exception e) {
				dueDate=null;
			}

			if ((deviceId == null || deviceId.length() == 0) && (userId == null || userId.length() == 0) &&  (transactionStatusId == null || transactionStatusId.length() == 0)) {
				isValidated = false;
				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						FrontConstants.DNAME + " " + FrontConstants.REQUIRED);

			}
			isValidated = true;

		}

		String strResponse = "";
		if (isValidated) {

			System.out.println(actionType);

			if (actionType.equals("getTransaction")) {

				String strURL = "http://127.0.0.1:9763/DeviceMgt_Service-1.0.0/services/device_mgt_services/transaction/gettransactions";
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(strURL);

				System.out.println(line);
				transactionRepoDAO = new TransactionRepoDAOImpl();
				LinkedList<Transaction> transactionList = transactionRepoDAO.getTransactionList(line, "Transaction");

				HttpSession session = request.getSession();

				session.setAttribute(BackendConstants.LOGIN, transactionList);
				requestDispatcher = request
						.getRequestDispatcher("gettransaction.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getSearch")) {

				String strSearch = request.getParameter("transactionId");
				System.out.println(strSearch);

				String restURL = "http://127.0.0.1:9763/DeviceMgt_Service-1.0.0/services/device_mgt_services/transaction/gettransaction/"+ strSearch;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				transactionRepoDAO = new TransactionRepoDAOImpl();
				LinkedList<Transaction> transactionList = transactionRepoDAO.getTransactionList(line, "Transaction");

				HttpSession session = request.getSession();

				session.setAttribute(BackendConstants.LOGIN, transactionList);
				requestDispatcher = request
						.getRequestDispatcher("gettransaction.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			}

			else if (actionType.equals("addTransaction")) {

				transaction = new Transaction();
				transaction.setDueDate(dueDate);
				transaction.setReturnDate(returnDate);
				transaction.setTransactionDate(transactionDate);
				transaction.setDeviceId(deviceId);
				transaction.setTransactionStatusId(transactionStatusId);
				transaction.setUserId(userId);
				
				
				
				String restURL = "http://192.168.43.204:9763/DeviceMgt_Service-1.0.0/services/device_mgt_services/transaction/addtransaction";

				transactionRepoDAO = new TransactionRepoDAOImpl();
				strResponse = transactionRepoDAO.addTransaction(transaction, restURL);
				out.print(strResponse);

			}

		} else {
			
			requestDispatcher = request.getRequestDispatcher("add_transaction.jsp");
			requestDispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

}