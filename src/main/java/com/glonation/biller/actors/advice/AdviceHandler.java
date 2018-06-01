package com.glonation.biller.actors.advice;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import akka.camel.CamelMessage;

public class AdviceHandler extends UntypedActor {
	
	private static final Logger logger = LoggerFactory.getLogger(AdviceHandler.class);
	private String workerId = getSelf().path().name();
	private String TAG = "[" + this.getClass().getSimpleName() + "-" + workerId + "] ";
	private Map<String, Map<String, Map<String, Object>>> abc;
	private ActorSystem system;
	private Map<String, ActorRef> actorMap;
	
	
	private ObjectMapper m;

	public AdviceHandler (
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
			CamelMessage msg = (CamelMessage) message;
			Camel camel = CamelExtension.get(system);
			CamelContext camelContext = camel.context();
			String body = msg.getBodyAs(String.class, camelContext);
			
//			arc = pa.getAdviceRequestCore(body);
			
			HashMap<String, Object> arcMap = m.readValue(body, HashMap.class);
			
			String billerId = arcMap.get("SWITCHING_BILLER_ID").toString();
			
			ActorRef act = actorMap.get(abc.get(billerId).get("advice").get("RequestActor").toString());
			logger.debug("|Performance|BillerAdapter|AdviceHandler|1|Advice|ORDER_ID:" + "" +"|TRX_ID:"+arcMap.get("TRX_ID")); 
			act.forward(message, getContext());
//			logger.debug(TAG + "Forwarding to Actor : " + act.path().name());
			logger.debug(TAG + "Forwarding to Actor : " + act.path().name() + ", BillerId : " + billerId + ", TRX_ID : " + arcMap.get("TRX_ID"));
		}
		else {
			String err = TAG + "Received unknown message : " + message.toString();
			if (err.length() > 499) {
				err = err.substring(0, 499);
			}
			logger.error(err);
			getSender().tell(err, getSelf());
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
