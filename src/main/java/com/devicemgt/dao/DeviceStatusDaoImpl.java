package com.devicemgt.dao;

import java.util.LinkedList;

import org.json.JSONObject;

import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.model.DeviceStatus;

public class DeviceStatusDaoImpl implements DeviceStatusDao {

	HttpAPICaller httpAPICaller;
	
	@Override
	public boolean isValidJSON(String json) {
		try {
			new JSONObject(json);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String deleteDeviceStatus(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("finally")
	@Override
	public LinkedList<DeviceStatus> getDeviceStatus(String jsonBody,String rootElement) {
		
		System.out.println(jsonBody);
		LinkedList<DeviceStatus> tsList = new LinkedList<DeviceStatus>();
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

				DeviceStatus tempTStatus = new DeviceStatus();				

				tempTStatus.setDeviceStatusId(jObject.getJSONObject(rootElement).get("deviceStatusId").toString());
						
				tempTStatus.setDeviceStatusName(jObject.getJSONObject(rootElement).get("deviceStatusName").toString());

				tsList.add(tempTStatus);
				
		}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			return tsList;
		}

	}

	@Override
	public String addDeviceStatus(DeviceStatus args0, String args1) {
		
		String payloadBoby = "{ \"DeviceStatus\":{\"deviceStatusName\":"+ args0.getDeviceStatusName()+"}}";
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(args1, payloadBoby);
		return strResponse;
	}
	
	

}