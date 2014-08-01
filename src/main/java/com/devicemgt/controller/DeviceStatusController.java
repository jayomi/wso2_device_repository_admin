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
import com.devicemgt.dao.DeviceStatusDao;
import com.devicemgt.dao.DeviceStatusDaoImpl;
import com.devicemgt.dao.TransactionRepoDAOImpl;
import com.devicemgt.dao.TransactionStatusDao;
import com.devicemgt.dao.TransactionStatusDaoImpl;
import com.devicemgt.model.DeviceStatus;
import com.devicemgt.model.Transaction;
import com.devicemgt.model.TransactionStatus;

/**
 * Servlet implementation class TransactionStatusController
 */
@WebServlet("/DeviceStatusController")
public class DeviceStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DeviceStatus ts;
	DeviceStatusDao deviceStatusDao;
	HttpAPICaller httpAPICaller;
	boolean isValidated = true;
	String strName;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeviceStatusController() {
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
		System.out.println(request.getParameter("adddevicestatus"));
		
		RequestDispatcher requestDispatcher = null;

		if (request.getParameter("getdevicestatus") != null) {

			actionType = "getdevicestatus";

		}else if (request.getParameter("getSearch") != null) {
	          actionType="getdevicestatus";
		}		
		else if (request.getParameter("adddevicestatus") != null) {
			actionType = "adddevicestatus";
		} else if (request.getParameter("deletedevicestatus") != null) {
			actionType = "deletedevicestatus";
		}

		if (actionType.equals("adddevicestatus")) {

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

			if (actionType.equals("getdevicestatus")) {

				String strURL = BackendConstants.SERVICEURL +"/devicestatus/getdevicestatuses";
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(strURL);

			/*	System.out.println(line);*/
				deviceStatusDao = new DeviceStatusDaoImpl();
				LinkedList<DeviceStatus> tsList = deviceStatusDao.getDeviceStatus(line, "DeviceStatus");

				HttpSession session = request.getSession();

				session.setAttribute(BackendConstants.LOGIN, tsList);
				requestDispatcher = request
						.getRequestDispatcher("getdevice_status.jsp");
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

				String restURL = BackendConstants.SERVICEURL +"/devicestatus/getdevicestatuses?tsId="
						+ strSearch;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				deviceStatusDao = new DeviceStatusDaoImpl();
				LinkedList<DeviceStatus> tsList = deviceStatusDao.getDeviceStatus(line, "DeviceStatus");

				HttpSession session = request.getSession();

				session.setAttribute("Transaction Status",tsList);
				requestDispatcher = request
						.getRequestDispatcher("getdevice_status.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			}

			else if (actionType.equals("adddevicestatus")) {

				ts = new DeviceStatus();
				ts.setDeviceStatusName(strName);				

				String restURL = BackendConstants.SERVICEURL +"/devicestatus/adddevicestatus";

				deviceStatusDao= new DeviceStatusDaoImpl();
				strResponse =deviceStatusDao.addDeviceStatus(ts, restURL);

				out.print(strResponse);

			

			} 

		} else {
			requestDispatcher = request
					.getRequestDispatcher("add_device_status.jsp");
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
	public LinkedList<DeviceStatus> getTransactionStatusFromDB(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<DeviceStatus> tsList = new LinkedList<DeviceStatus>();
		try {

			String strURL = BackendConstants.SERVICEURL +"/devicestatus/getdevicestatuses";
			httpAPICaller = new HttpAPICaller();
			String line = httpAPICaller.getRequest(strURL);

			System.out.println(line);
			deviceStatusDao = new DeviceStatusDaoImpl();
			tsList = deviceStatusDao.getDeviceStatus(line, "DeviceStatus");

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			return tsList;
		}

	}

}