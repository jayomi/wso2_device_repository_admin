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

import com.devicemgt.dao.DeviceDAO;
import com.devicemgt.dao.DeviceRepoDAOImpl;
import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.dao.TransactionRepoDAO;
import com.devicemgt.dao.TransactionRepoDAOImpl;
import com.devicemgt.dao.TransactionStatusDao;
import com.devicemgt.dao.TransactionStatusDaoImpl;
import com.devicemgt.model.Device;
import com.devicemgt.model.Transaction;
import com.devicemgt.model.TransactionStatus;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;
import com.devicemgt.util.Rest;

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
		    }  else if (request.getParameter("deleteBtn") != null) {
				actionType = "deleteTransaction";
			} else if (request.getParameter("editBtn") != null) {
				actionType = "editTransaction";
			} else if (request.getParameter("updateBtn") != null) {
				actionType = "updateTransaction";
			}

		 if ((actionType.equals("addTransaction"))
					|| (actionType.equals("updateTransaction"))) {

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
			
			

			if (actionType.equals("getTransaction")) {

				String strURL = Rest.getProperty() + "/transaction/gettransactionsdetail";
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(strURL);

				transactionRepoDAO = new TransactionRepoDAOImpl();
				LinkedList<Transaction> transactionList = transactionRepoDAO.getTransactionList(line, "Transaction");

				HttpSession session = request.getSession();

				session.setAttribute(BackendConstants.LOGIN, transactionList);
				requestDispatcher = request
						.getRequestDispatcher("gettransaction.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getSearch")) {

				String restURL =null;
				String strSearch = request.getParameter("searchDevice");
				strSearch = strSearch.replace(" ", "%20"); 
				
				String strSearch2 = request.getParameter("searchStatus");
				strSearch2 = strSearch2.replace(" ", "%20"); 
				
				
				if(( strSearch != "" ) && ( strSearch2 != "") )
				{
					restURL = Rest.getProperty() + "/transaction/gettransactionsdetail?deviceId="+ strSearch + "&statusId=" + strSearch2;
				} else if(( strSearch == "") && ( strSearch2 != "" ) )
				{
					restURL = Rest.getProperty() + "/transaction/gettransactionsdetail?statusId=" + strSearch2;
				} 
				else if(( strSearch2 == "" ) && ( strSearch != "" ) )
				{
					restURL = Rest.getProperty() + "/transaction/gettransactionsdetail?deviceId="+ strSearch;
				} else 
				{
					restURL = Rest.getProperty() + "/transaction/gettransactionsdetail";
				}
				
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				transactionRepoDAO = new TransactionRepoDAOImpl();
				LinkedList<Transaction> transactionList = transactionRepoDAO.getTransactionList(line, "Transaction");
	
				HttpSession session = request.getSession();
				session.setAttribute(BackendConstants.LOGIN, transactionList);
				requestDispatcher = request.getRequestDispatcher("gettransaction.jsp");
				requestDispatcher.forward(request, response);

			}

			else if (actionType.equals("addTransaction")) {

				transaction = new Transaction();
				transaction.setDueDate(dueDate);
				transaction.setReturnDate(returnDate);
				transaction.setTransactionDate(transactionDate);
				transaction.setDeviceId(deviceId);
				transaction.setTransactionStatusId(transactionStatusId);
				transaction.setUserId(userId);
				
				String restURL = Rest.getProperty() + "/transaction/addtransaction";

				transactionRepoDAO = new TransactionRepoDAOImpl();
				strResponse = transactionRepoDAO.addTransaction(transaction, restURL);
				out.print(strResponse);
				
				
				String strURL = Rest.getProperty() + "/transaction/gettransactionsdetail";
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(strURL);

				transactionRepoDAO = new TransactionRepoDAOImpl();
				LinkedList<Transaction> transactionList = transactionRepoDAO.getTransactionList(line, "Transaction");

				HttpSession session = request.getSession();

				session.setAttribute(BackendConstants.LOGIN, transactionList);
				
				
				requestDispatcher = request
						.getRequestDispatcher("gettransaction.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("loadList")) {
				
						
				String strURL = Rest.getProperty() + "/device/getdevices";
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(strURL);

				DeviceDAO deviceRepoDAO;
				deviceRepoDAO =  new DeviceRepoDAOImpl();
				LinkedList<Device> deviceList = new LinkedList<Device>();
				deviceList = deviceRepoDAO.getDeviceList(line, "Device");

				
				String strURL2 = Rest.getProperty() + "/transactionstatus/gettransactionstatus";
				httpAPICaller = new HttpAPICaller();
				String line2 = httpAPICaller.getRequest(strURL2);

				TransactionStatusDao statusRepoDAO;
				statusRepoDAO =  new TransactionStatusDaoImpl();
				LinkedList<TransactionStatus> statusList = new LinkedList<TransactionStatus>();
				statusList = statusRepoDAO.getTransactionStatus(line2, "TransactionStatus");

				try {

					HttpSession session = request.getSession();

					session.setAttribute("DeviceListShow", deviceList);
					session.setAttribute("StatusListShow", statusList);
					
					
					requestDispatcher = request.getRequestDispatcher("get_transaction.jsp");
					requestDispatcher.forward(request, response);
					
					
				
				}catch(Exception e){
					e.printStackTrace();
				}
				}
			
			
			else if (actionType.equals("getTransactionOnLoad")) {

				LinkedList<Transaction> transactionList = getTransactionFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("TransactionList", transactionList);
				requestDispatcher = request
						.getRequestDispatcher("updateordelete_transaction.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("deleteTransaction")) {

				String strDltRadio = request.getParameter("deleteTransaction");

				String restURL = Rest.getProperty() + "/transaction/deletetransaction/"+ strDltRadio;
		
				transactionRepoDAO = new TransactionRepoDAOImpl();
				strResponse = ((TransactionRepoDAOImpl) transactionRepoDAO).deleteTransaction(restURL);
				
				LinkedList<Transaction> transactionList = getTransactionFromDB(request,
						response);
				HttpSession session = request.getSession();

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);
				session.setAttribute("TransactionList", transactionList);
				requestDispatcher = request
						.getRequestDispatcher("updateordelete_transaction.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("editTransaction")) {

				String strBtnEdit = request.getParameter("editTransaction");

				String restURL = Rest.getProperty() + "/transaction/gettransaction/"+ strBtnEdit;
				
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);
				
				line = line.replace("{\"Transaction\":", " {\"Transaction\": [");
				line = line.replace("+", " ");
				line = line.substring(0, line.length()-1);
				line= line + " ] }";
			
				transaction = new Transaction();
				transaction.setDueDate(dueDate);
				transaction.setReturnDate(returnDate);
				transaction.setTransactionDate(transactionDate);
				transaction.setDeviceId(deviceId);
				transaction.setTransactionStatusId(transactionStatusId);
				transaction.setUserId(userId);
				
				transactionRepoDAO = new TransactionRepoDAOImpl();
				

				LinkedList<Transaction> transactionList = transactionRepoDAO.getTransactionList(
						line, "Transaction");
	
				HttpSession session = request.getSession();

				session.setAttribute("TransactionList", transactionList);
	
				requestDispatcher = request
						.getRequestDispatcher("edit_transaction.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			} else if (actionType.equals("updateTransaction")) {

				String strID = request.getParameter("transactionId");


				transaction = new Transaction();
				transaction.setDueDate(dueDate);
				transaction.setReturnDate(returnDate);
				transaction.setTransactionDate(transactionDate);
				transaction.setDeviceId(deviceId);
				transaction.setTransactionStatusId(transactionStatusId);
				transaction.setUserId(userId);

				String restURL = Rest.getProperty() + "/transaction/updatetransaction/"+ strID;

				transactionRepoDAO = new TransactionRepoDAOImpl();
				strResponse = ((TransactionRepoDAOImpl) transactionRepoDAO).updateTransaction(transaction, restURL);

				HttpSession session = request.getSession();
				session.setAttribute(BackendConstants.ERROR_MESSAGE,strResponse);

				LinkedList<Transaction> transactionList = getTransactionFromDB(request,
						response);
				session.setAttribute("TransactionList", transactionList);
				requestDispatcher = request
						.getRequestDispatcher("updateordelete_transaction.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("loadTransaction")) {


				LinkedList<Transaction> transactionList = getTransactionFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("TransactionList", transactionList);
				requestDispatcher = request
						.getRequestDispatcher("get_transaction.jsp");
				requestDispatcher.forward(request, response);

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
	
	
	public LinkedList<Transaction> getTransactionFromDB(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<Transaction> transactionList = new LinkedList<Transaction>();
		try {

			String strURL = Rest.getProperty() + "/transaction/gettransactionsdetail";
			httpAPICaller = new HttpAPICaller();
			String line = httpAPICaller.getRequest(strURL);

			transactionRepoDAO = new TransactionRepoDAOImpl();
			transactionList = transactionRepoDAO.getTransactionList(line, "Transaction");

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			return transactionList;
		}

	}

}