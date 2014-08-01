package com.devicemgt.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import org.json.JSONObject;

import com.devicemgt.model.Transaction;





public class TransactionRepoDAOImpl implements TransactionRepoDAO {

	HttpAPICaller httpAPICaller;

	@Override
	public LinkedList<Transaction> getTransactionList(String jsonBody, String rootElement) {

		System.out.println(jsonBody);
		System.out.println(rootElement);
		
		LinkedList<Transaction> TransactionList = new LinkedList<Transaction>();
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
				jObject.put(rootElement, jsonObject.getJSONArray(rootElement).get(x));

				Transaction tempTransaction = new Transaction();
		
				tempTransaction.setDeviceId(jObject.getJSONObject(rootElement).get("deviceId").toString());
				
				try{
				tempTransaction.setDueDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jObject.getJSONObject(rootElement).get("dueDate").toString().replace("T", " ").replace("+", " ")));
				}
				catch(Exception e)
				{
					
				}
				try{
					tempTransaction.setReturnDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jObject.getJSONObject(rootElement).get("returnDate").toString().replace("T", " ").replace("+", " ")));
					}
					catch(Exception e)
					{
						
					}
				
				try{
					tempTransaction.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jObject.getJSONObject(rootElement).get("transactionDate").toString().replace("T", " ").replace("+", " ")));
					}
					catch(Exception e)
					{
						
					}
				
	
			
				
				
				tempTransaction.setTransactionId(jObject.getJSONObject(rootElement).get("transactionId").toString());
				
				
				tempTransaction.setUserId(jObject.getJSONObject(rootElement).get("userId").toString());

				tempTransaction.setTransactionStatusId(jObject.getJSONObject(rootElement).get("transactionStatusId").toString());
								
				
				TransactionList.add(tempTransaction);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			return TransactionList;
		}

	}

	@Override
	public String addTransaction(Transaction transaction, String restURL) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String strDates = null;
		
		try
		{
			strDates = ",\"dueDate\":"+dateFormat.format(transaction.getDueDate());
		}
		catch(Exception e)
		{}
		
		try
		{
			strDates = strDates + ",\"returnDate\"" + ":"+dateFormat.format(transaction.getReturnDate());
		}
		catch(Exception e)
		{}
		
		try
		{
			strDates = strDates + ",\"transactionDate\":"  +dateFormat.format(transaction.getTransactionDate());
		}
		catch(Exception e)
		{}
		
		String payloadBody =null;
		if (strDates ==null)
		{
			payloadBody = "{\"Transaction\":{\"deviceId\":"+transaction.getDeviceId()+
					",\"transactionStatusId\":"+
					transaction.getTransactionStatusId()+",\"userId\":"+transaction.getUserId()+"}}";
		
		}
		else
		{
			payloadBody = "{\"Transaction\":{\"deviceId\":"+transaction.getDeviceId()+
					strDates +",\"transactionStatusId\":"+
					transaction.getTransactionStatusId()+",\"userId\":"+transaction.getUserId()+"}}";
		}
		
		 

		
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(restURL, payloadBody);

		return strResponse;
	}

	public String deleteTransaction(String restURL) {
		
		httpAPICaller = new HttpAPICaller();
		String strResponse=httpAPICaller.deleteRequest(restURL);
		
		return strResponse;
	}

	public boolean isValidJSON(String json) {
		try {
			new JSONObject(json);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	
	
	public String updateTransaction(Transaction transaction, String restURL) {
		
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String strDates = null;
		
		try
		{
			strDates = ",\"dueDate\":"+dateFormat.format(transaction.getDueDate());
		}
		catch(Exception e)
		{}
		
		try
		{
			strDates = strDates + ",\"returnDate\"" + ":"+dateFormat.format(transaction.getReturnDate());
		}
		catch(Exception e)
		{}
		
		try
		{
			strDates = strDates + ",\"transactionDate\":"  +dateFormat.format(transaction.getTransactionDate());
		}
		catch(Exception e)
		{}
		
		String payloadBody =null;
		if (strDates ==null)
		{
			payloadBody = "{\"Transaction\":{\"deviceId\":"+transaction.getDeviceId()+
					",\"transactionStatusId\":"+
					transaction.getTransactionStatusId()+",\"userId\":"+transaction.getUserId()+"}}";
		
		}
		else
		{
			payloadBody = "{\"Transaction\":{\"deviceId\":"+transaction.getDeviceId()+
					strDates +",\"transactionStatusId\":"+
					transaction.getTransactionStatusId()+",\"userId\":"+transaction.getUserId()+"}}";
		}

		System.out.println("payloadBody");
		System.out.println(payloadBody);
		
		System.out.println(" put findal restURL");
		System.out.println(restURL);
		
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.putRequest(restURL, payloadBody);

		return strResponse;
	}

	public Transaction getTransaction(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}


}
