package com.devicemgt.dao;

import java.util.LinkedList;

import org.json.JSONObject;

import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.model.TransactionStatus;

public class TransactionStatusDaoImpl implements TransactionStatusDao {

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
	public String deleteTransactionStatus(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("finally")
	@Override
	public LinkedList<TransactionStatus> getTransactionStatus(String jsonBody,String rootElement) {


		LinkedList<TransactionStatus> tsList = new LinkedList<TransactionStatus>();
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

				TransactionStatus tempTStatus = new TransactionStatus();				

				tempTStatus.setTransactionStatusId(jObject.getJSONObject(rootElement).get("transactionStatusId").toString());
						
				tempTStatus.setTransactionStatusName(jObject.getJSONObject(rootElement).get("transactionStatusName").toString());

				tsList.add(tempTStatus);
				
		}
		} catch (Exception e) {
		} finally {
			return tsList;
		}

	}

	@Override
	public String addDevice(TransactionStatus args0, String args1) {
		
		String payloadBoby = "{ \"TransactionStatus\":{\"transactionStatusName\":"+ args0.getTransactionStatusName()+"}}";
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(args1, payloadBoby);
		return strResponse;
	}
	
	

}