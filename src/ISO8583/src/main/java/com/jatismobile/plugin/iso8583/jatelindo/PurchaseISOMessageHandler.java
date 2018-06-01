package com.jatismobile.plugin.iso8583.jatelindo;

import com.jatismobile.plugin.iso8583.DBConfiguration;
import com.jatismobile.plugin.iso8583.interfaces.AbstractISOMessageHandler;

public class PurchaseISOMessageHandler extends AbstractISOMessageHandler{

	private PurchaseResponseMapper responseMapper;

	public PurchaseISOMessageHandler(DBConfiguration dbConfig) {
		super(dbConfig);
	}

	public void setResponseMapper(PurchaseResponseMapper responseMapper) {
		this.responseMapper = responseMapper;  
	}

	public PurchaseResponseMapper getResponseMapper() {
		return responseMapper;
	}
}
