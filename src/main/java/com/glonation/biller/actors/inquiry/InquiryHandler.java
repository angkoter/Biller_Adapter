package com.glonation.biller.actors.inquiry;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glonation.biller.AdapterSettings;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import akka.camel.CamelMessage;

public class InquiryHandler extends UntypedActor {

	private static final Logger logger = LoggerFactory.getLogger(InquiryHandler.class);
	private String workerId = getSelf().path().name();
	private String TAG = "[" + this.getClass().getSimpleName() + "-" + workerId + "] ";
	private AdapterSettings as;
	private Map<String, Map<String, Map<String, Object>>> abc;
//	private InquiryRequestCore irc;
	private ActorSystem system;
	private ActorRef inquiryHitterXMLGen;

	private Map<String, ActorRef> actorMap;

//	private HashMap<String, Object> ircMap;

	private ObjectMapper m;
	
	public InquiryHandler (
			Map<String, Map<String, Map<String, Object>>> abc,
			ActorSystem system,
			Map<String, ActorRef> actorMap) {
		
		this.abc = abc;
		this.system = system;
		this.actorMap = actorMap;
		this.m = new ObjectMapper();
}
	
	@Override
	public void onReceive(Object message) throws Throwable {
		// TODO Auto-generated method stub
		if (message instanceof CamelMessage) {
//		if (message instanceof String) {
			CamelMessage msg = (CamelMessage) message;
			Camel camel = CamelExtension.get(system);
			CamelContext camelContext = camel.context();
			String body = msg.getBodyAs(String.class, camelContext);

//			irc = pa.getInquiryRequestCore(body);
			
			HashMap<String, Object> ircMap = m.readValue(body, HashMap.class);
			
			String billerId = ircMap.get("SWITCHING_BILLER_ID").toString();
			
			ActorRef act = actorMap.get(abc.get(billerId).get("inquiry").get("RequestActor").toString());
			
			act.forward(message, getContext());
//			logger.debug(TAG + "Forwarding to Actor : " + act.path().name());
                        logger.debug("|Performance|BillerAdapter|InquiryHandler|1|Inquiry|ORDER_ID:" + "" +"|TRX_ID:"+ircMap.get("TRX_ID"));    
			logger.debug(TAG + "Forwarding to Actor : " + act.path().name() + ", BillerId : " + billerId + ", TRX_ID : " + ircMap.get("TRX_ID"));
		}
		else {
			logger.debug(TAG + "Received unknown message : " + message.toString());
		}
	}

	@Override
	public void postStop() throws Exception {
		// TODO Auto-generated method stub
		super.postStop();
	}

	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		
	}
}