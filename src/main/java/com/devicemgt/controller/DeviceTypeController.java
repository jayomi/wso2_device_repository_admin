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

import com.devicemgt.dao.DeviceTypeDAO;
import com.devicemgt.dao.DeviceTypeDAOImpl;
import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.model.DeviceType;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;
import com.devicemgt.util.Rest;

/**
 * Servlet implementation class DeviceTypeController
 */
@WebServlet("/DeviceTypeController")
public class DeviceTypeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DeviceType deviceType;
	DeviceTypeDAO deviceTypeDAO;
	HttpAPICaller httpAPICaller;
	boolean isValidated = true;
	String strdeviceTypeName;
	String strdeviceTypeDescription;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeviceTypeController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("Hello from Servlet\n\n");

		String actionType = (String) request.getSession(false).getAttribute(
				"actionType");
		
		System.out.println("Before validate : " +actionType);
		// getDvices , getSearch

		RequestDispatcher requestDispatcher = null;

		if (request.getParameter("getDeviceType") != null) {

			actionType = "getDeviceType";

		} else if (request.getParameter("getSearch") != null) {
			actionType = "getSearch";
		} else if (request.getParameter("deleteBtn") != null) {
			actionType = "deleteDevice";
		} else if (request.getParameter("editBtn") != null) {
			actionType = "editDevice";
		} else if (request.getParameter("updateBtn") != null) {
			actionType = "updateDevice";
		}

		if ((actionType.equals("addDeviceType"))) {

			String strNoDescription = "No Description";
			strdeviceTypeName = request.getParameter("name");
			strdeviceTypeDescription = request.getParameter("description");

			if (strdeviceTypeName == null || strdeviceTypeName.length() == 0) {
				isValidated = false;
				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						FrontConstants.DNAME + " " + FrontConstants.REQUIRED);

			} else if (strdeviceTypeDescription == null
					|| strdeviceTypeDescription.length() == 0) {
				strdeviceTypeDescription = strNoDescription;

			}
		}
			String strResponse = "";
			if (isValidated) {

				System.out.println("Action type : " + actionType);

				if (actionType.equals("loadDeviceType")) {

					LinkedList<DeviceType> deviceTypeList = getDeviceTypeFromDB(
							request, response);
					HttpSession session = request.getSession();
					session.setAttribute("deviceTypeList", deviceTypeList);
					requestDispatcher = request
							.getRequestDispatcher("getdevicetype.jsp");
					requestDispatcher.forward(request, response);

				} 

				else if (actionType.equals("addDeviceType")) {

					deviceType = new DeviceType();
					deviceType.setDeviceTypeName(strdeviceTypeName);
					deviceType
							.setDeviceTypeDescription(strdeviceTypeDescription);

					String restURL = Rest.getProperty() +"/devicetype/adddevicetype";

					deviceTypeDAO = new DeviceTypeDAOImpl();
					strResponse = deviceTypeDAO.addDeviceType(deviceType,
							restURL);

					request.setAttribute(BackendConstants.ERROR_MESSAGE,
							strResponse);
					requestDispatcher = request
							.getRequestDispatcher("add_devicetype.jsp");
					requestDispatcher.forward(request, response);

					// out.print(strResponse);

				}

				else if (actionType.equals("loadDevice")) {

					LinkedList<DeviceType> deviceTypeList = getDeviceTypeFromDB(
							request, response);
					HttpSession session = request.getSession();

					session.setAttribute("deviceTypeList", deviceTypeList);
					requestDispatcher = request
							.getRequestDispatcher("get_devicetype.jsp");
					requestDispatcher.forward(request, response);

				}

			} else {
				requestDispatcher = request
						.getRequestDispatcher("add_devicetype.jsp");
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
	public LinkedList<DeviceType> getDeviceTypeFromDB(
			HttpServletRequest request, HttpServletResponse response) {

		LinkedList<DeviceType> deviceTypeList = new LinkedList<DeviceType>();
		try {

			String strURL = Rest.getProperty() +"/devicetype/getdevicetypes";
			httpAPICaller = new HttpAPICaller();
			String line = httpAPICaller.getRequest(strURL);

			System.out.println(line);
			deviceTypeDAO = new DeviceTypeDAOImpl();
			deviceTypeList = deviceTypeDAO
					.getDeviceTypeList(line, "DeviceType");
			

			

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			return deviceTypeList;
		}

	}

}