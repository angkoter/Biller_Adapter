package com.glonation.biller.messages;

import java.util.LinkedHashMap;

public class GeneralResponse {
	
	public static class Inquiry {

		public static final String INQUIRY_ID = "INQUIRY_ID";
		public static final String INQUIRY_STATUS = "INQUIRY_STATUS";
		public static final String INQUIRY_STATUS_DESC = "INQUIRY_STATUS_DESC";
		public static final String SUBSCRIBER_NUMBER = "SUBSCRIBER_NUMBER";
		public static final String SUBSCRIBER_NAME = "SUBSCRIBER_NAME";
		public static final String ADMIN_FEE = "ADMIN_FEE";
		public static final String TRX_AMOUNT = "TRX_AMOUNT";
		public static final String BILL_ID = "BILL_ID";
		public static final String BILLER_REAL_RESPONSE = "BILLER_REAL_RESPONSE";
		public static final String BILL_INFO = "BILL_INFO";

		public static LinkedHashMap<String, String> getInitialResponse () {
			
			LinkedHashMap<String, String> output = new LinkedHashMap<String, String>();
			output.put(INQUIRY_ID, "");
			output.put(INQUIRY_STATUS, "");
			output.put(INQUIRY_STATUS_DESC, "");
			output.put(SUBSCRIBER_NUMBER, "");
			output.put(SUBSCRIBER_NAME, "");
			output.put(ADMIN_FEE, "");
			output.put(TRX_AMOUNT, "");
			output.put(BILL_ID, "");
			output.put(BILLER_REAL_RESPONSE, "");
			output.put(BILL_INFO, "");

			return output;
		}
	}
	
	public static class Purchase{

		public static final String PURCHASE_STATUS = "PURCHASE_STATUS";
		public static final String PURCHASE_STATUS_DESC = "PURCHASE_STATUS_DESC";
		public static final String REFERENCE_NUMBER = "REFERENCE_NUMBER";
		public static final String PURCHASE_TIME = "PURCHASE_TIME";
		public static final String BILLER_REAL_RESPONSE = "BILLER_REAL_RESPONSE";
		public static final String BILL_INFO = "BILL_INFO";

		public static LinkedHashMap<String, String> getInitialResponse () {

			LinkedHashMap<String, String> output = new LinkedHashMap<String, String>();
			output.put(PURCHASE_STATUS, "");
			output.put(PURCHASE_STATUS_DESC, "");
			output.put(REFERENCE_NUMBER, "");
			output.put(PURCHASE_TIME, "");
			output.put(BILLER_REAL_RESPONSE, "");
			output.put(BILL_INFO, "");

			return output;
		}
	}
	
	public static class Advice{

		public static final String PURCHASE_STATUS = "PURCHASE_STATUS";
		public static final String PURCHASE_STATUS_DESC = "PURCHASE_STATUS_DESC";
		public static final String REFERENCE_NUMBER = "REFERENCE_NUMBER";
		public static final String PURCHASE_TIME = "PURCHASE_TIME";
		public static final String BILLER_REAL_RESPONSE = "BILLER_REAL_RESPONSE";
		public static final String BILL_INFO = "BILL_INFO";

		public static LinkedHashMap<String, String> getInitialResponse () {

			LinkedHashMap<String, String> output = new LinkedHashMap<String, String>();
			output.put(PURCHASE_STATUS, "");
			output.put(PURCHASE_STATUS_DESC, "");
			output.put(REFERENCE_NUMBER, "");
			output.put(PURCHASE_TIME, "");
			output.put(BILLER_REAL_RESPONSE, "");
			output.put(BILL_INFO, "");

			return output;
		}
	}
}
