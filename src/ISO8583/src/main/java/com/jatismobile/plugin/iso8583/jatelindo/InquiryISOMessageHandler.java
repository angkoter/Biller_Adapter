package com.jatismobile.plugin.iso8583.jatelindo;

import com.jatismobile.plugin.iso8583.DBConfiguration;
import com.jatismobile.plugin.iso8583.interfaces.AbstractISOMessageHandler;

public class InquiryISOMessageHandler extends AbstractISOMessageHandler{

	private InquiryResponseMapper responseMapper;

	public InquiryISOMessageHandler(DBConfiguration dbConfig) {
		super(dbConfig);
	}

	public void setResponseMapper(InquiryResponseMapper responseMapper) {
		this.responseMapper = responseMapper;
	}
	
	public InquiryResponseMapper getResponseMapper() {
		return responseMapper;
	}
}
