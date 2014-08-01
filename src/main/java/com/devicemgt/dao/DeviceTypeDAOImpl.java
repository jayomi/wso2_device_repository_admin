package com.devicemgt.dao;

import java.util.LinkedList;

import org.json.JSONObject;

import com.devicemgt.model.DeviceType;

public class DeviceTypeDAOImpl implements DeviceTypeDAO{
	
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
	public LinkedList<DeviceType> getDeviceTypeList(String jsonBody,
			String rootElement) {
		

		System.out.println(jsonBody);
		LinkedList<DeviceType> deviceTypeList = new LinkedList<DeviceType>();
		
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

				DeviceType tempDeviceType = new DeviceType();
				
				tempDeviceType.setDeviceTypeDescription(jObject.getJSONObject(rootElement)
						.get("deviceTypeDescription").toString());			
				
				tempDeviceType.setDeviceTypeId((jObject.getJSONObject(rootElement)
						.get("deviceTypeId").toString()));
				
				
				tempDeviceType.setDeviceTypeName((jObject.getJSONObject(rootElement)
						.get("deviceTypeName").toString()));
				
				

				deviceTypeList.add(tempDeviceType);
			}


		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			return deviceTypeList;
		}

	}
		
		
	

	@Override
	public String addDeviceType(DeviceType arg0, String arg1) {
		

		String payloadBoby = "{DeviceType: {deviceTypeDescription: "
				+ arg0.getDeviceTypeDescription() + "," + "deviceTypeName: "
				+ arg0.getDeviceTypeName() + "}}";

		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(arg1, payloadBoby);

		return strResponse;
		
	
	}

	
}
