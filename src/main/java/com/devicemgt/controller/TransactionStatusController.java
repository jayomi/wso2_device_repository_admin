package com.devicemgt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;
import com.devicemgt.util.Rest;
import com.devicemgt.dao.TransactionRepoDAOImpl;
import com.devicemgt.dao.TransactionStatusDao;
import com.devicemgt.dao.TransactionStatusDaoImpl;
import com.devicemgt.model.Transaction;
import com.devicemgt.model.TransactionStatus;

/**
 * Servlet implementation class TransactionStatusController
 */
@WebServlet("/TransactionStatusController")
public class TransactionStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	TransactionStatus ts;
	TransactionStatusDao transactionStatusDao;
	HttpAPICaller httpAPICaller;
	boolean isValidated = true;
	String strName;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionStatusController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("Hello from Servlet\n\n");

		String actionType = (String)request.getSession(false).getAttribute("actionType");
		// getDvices , getSearch

		System.out.println("actionType");
		System.out.println(request.getParameter("addtransactionstatus"));
		
		RequestDispatcher requestDispatcher = null;

		if (request.getParameter("gettransactionstatus") != null) {

			actionType = "gettransactionstatus";

		}else if (request.getParameter("getSearch") != null) {
	          actionType="gettransactionstatus";
		}		
		else if (request.getParameter("addtransactionstatus") != null) {
			actionType = "addtransactionstatus";
		} else if (request.getParameter("deletetransactionstatus") != null) {
			actionType = "deletetransactionstatus";
		}

		if (actionType.equals("addtransactionstatus")) {

			strName = request.getParameter("statusName");
			
			if (strName == null || strName.length() == 0) {
				isValidated = false;
				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						FrontConstants.DNAME + " " + FrontConstants.REQUIRED);		

		}
		}

		String strResponse = "";
		
		System.out.println("isValidated");
		System.out.println(isValidated);
		
		if (isValidated) {

			System.out.println("Action type : " + actionType);

			if (actionType.equals("gettransactionstatus")) {

				String strURL = Rest.getProperty() +"/transactionstatus/gettransactionstatus";
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(strURL);

			/*	System.out.println(line);*/
				transactionStatusDao = new TransactionStatusDaoImpl();
				LinkedList<TransactionStatus> tsList = transactionStatusDao.getTransactionStatus(line, "TransactionStatus");

				HttpSession session = request.getSession();

				session.setAttribute(BackendConstants.LOGIN, tsList);
				requestDispatcher = request
						.getRequestDispatcher("gettransaction_status.jsp");
				requestDispatcher.forward(request, response);
				/*
				LinkedList<TransactionStatus > tsList = getTransactionStatusFromDB(request,response);
				HttpSession session = request.getSession();

				session.setAttribute("TransactionStatus", tsList);
				requestDispatcher = request.getRequestDispatcher("gettransaction_status.jsp");
				requestDispatcher.forward(request, response);
*/
			} else if (actionType.equals("getSearch")) {

				String strSearch = request.getParameter("tsId");
				System.out.println(strSearch);

				String restURL = Rest.getProperty() +"/transactionstatus/gettransactionstatus?tsId="
						+ strSearch;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				transactionStatusDao = new TransactionStatusDaoImpl();
				LinkedList<TransactionStatus> tsList = transactionStatusDao.getTransactionStatus(line, "TransactionStatus");

				HttpSession session = request.getSession();

				session.setAttribute("Transaction Status",tsList);
				requestDispatcher = request
						.getRequestDispatcher("gettransaction_status.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			}

			else if (actionType.equals("addtransactionstatus")) {

				ts = new TransactionStatus();
				ts.setTransactionStatusName(strName);				

				String restURL = Rest.getProperty() +"/transactionstatus/addtransactionstatus";

				transactionStatusDao= new TransactionStatusDaoImpl();
				strResponse =transactionStatusDao.addDevice(ts, restURL);

				out.print(strResponse);

			

			} 

		} else {
			requestDispatcher = request
					.getRequestDispatcher("add_transaction_status.jsp");
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

	@SuppressWarnings("finally")
	public LinkedList<TransactionStatus> getTransactionStatusFromDB(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<TransactionStatus> tsList = new LinkedList<TransactionStatus>();
		try {

			String strURL = Rest.getProperty() +"/transactionstatus/gettransactionstatus";
			httpAPICaller = new HttpAPICaller();
			String line = httpAPICaller.getRequest(strURL);

			System.out.println(line);
			transactionStatusDao = new TransactionStatusDaoImpl();
			tsList = transactionStatusDao.getTransactionStatus(line, "TransactionStatus");

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			return tsList;
		}

	}

}