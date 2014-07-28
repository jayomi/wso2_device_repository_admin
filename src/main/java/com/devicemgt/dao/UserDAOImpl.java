package com.devicemgt.dao;

import java.util.LinkedList;

import org.json.JSONObject;

import com.devicemgt.model.Device;
import com.devicemgt.model.User;

public class UserDAOImpl implements UserDAO {

	HttpAPICaller httpAPICaller;

	@Override
	public String addUser(User user, String restURL) {
		String payloadBody = "{user: {description: " + user.getDescription()
				+ ",email: " + user.getEmail() + ",password: "
				+ user.getPasssword() + ",telNo: " + user.getTelNo()
				+ ",userFname: " + user.getUserFname() + ",userLname: "
				+ user.getUserLname() + ",username: " + user.getUsername()
				+ "}}";

		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(restURL, payloadBody);

		return strResponse;
	}

	@Override
	public LinkedList<User> getUserList(String jsonBody, String rootElement) {
		System.out.println(jsonBody);
		LinkedList<User> userList = new LinkedList<User>();
		try {
			JSONObject jsonObject = null;

			if (isValidJSON(jsonBody)) {

				jsonObject = new JSONObject(jsonBody);
			} else {
				jsonObject = new JSONObject();
				jsonObject.put(rootElement, jsonBody);

			}

			for (int x = 0; x < jsonObject.getJSONArray(rootElement).length(); x++) {
				JSONObject jObject = new JSONObject();
				jObject.put(rootElement, jsonObject.getJSONArray(rootElement)
						.get(x));

				Device tempDevice = new Device();
				User user = new User();

				// user.setDescription(jObject.getJSONObject(rootElement)
				// .get("description").toString());
				user.setEmail(jObject.getJSONObject(rootElement).get("email")
						.toString());
				user.setRole(jObject.getJSONObject(rootElement).get("role")
						.toString());
				user.setTelNo(jObject.getJSONObject(rootElement).get("telNo")
						.toString());
				user.setUserFname(jObject.getJSONObject(rootElement)
						.get("userFname").toString());
				user.setUserId(jObject.getJSONObject(rootElement).get("userId")
						.toString());
				user.setUserLname(jObject.getJSONObject(rootElement)
						.get("userLname").toString());
				user.setUsername(jObject.getJSONObject(rootElement)
						.get("username").toString());

				userList.add(user);
			}

			// for (int y = 0; y < deviceList.size(); y++) {
			// System.out.println("Number " + y);
			// System.out.println(deviceList.get(y).getId());
			// System.out.println(deviceList.get(y).getStatusId());
			// System.out.println(deviceList.get(y).getTypeId());
			// System.out.println(deviceList.get(y).getDescription());
			// System.out.println(deviceList.get(y).getName());
			// System.out.println();
			//
			// }

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			return userList;
		}

	}

	public boolean isValidJSON(String json) {
		try {
			new JSONObject(json);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String deleteUser(String arg0) {
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.deleteRequest(arg0);

		return strResponse;
	}

	@Override
	public String updateUser(User arg0, String arg1) {
		String payloadBody = "{user: {description: " + arg0.getDescription()
				+ ",email: " + arg0.getEmail() + ",password: "
				+ arg0.getPasssword() + ",telNo: " + arg0.getTelNo()
				+ ",userFname: " + arg0.getUserFname() + ",userLname: "
				+ arg0.getUserLname() + ",username: " + arg0.getUsername()
				+ "}}";

		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.putRequest(arg1, payloadBody);
		return strResponse;
	}

}
