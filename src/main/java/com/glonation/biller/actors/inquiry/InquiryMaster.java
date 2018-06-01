package com.glonation.biller.actors.inquiry;

import java.util.HashMap;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glonation.biller.AdapterSettings;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;

public class InquiryMaster extends UntypedActor {
	
	private static final Logger logger = LoggerFactory.getLogger(InquiryMaster.class);
	private ActorRef inquiryHandler;
	private AdapterSettings as;
	private Map<String, Map<String, Map<String, Object>>> adapterBillerConfiguration;
	private ActorRef inquiryConsumer;
	private ActorSystem system;
	
	@Override
	public void onReceive(Object arg0) throws Throwable {
		// TODO Auto-generated method stub
		
	}
	
	public InquiryMaster (AdapterSettings as,
						  Map<String, Map<String, Map<String, Object>>> adapterBillerConfiguration,
						  ActorSystem system) {
			this.as = as;
			this.adapterBillerConfiguration = adapterBillerConfiguration;
			this.system = system;
			
	}
	
	@Override
	public void postStop() throws Exception {
		// TODO Auto-generated method stub
		super.postStop();
	}

	@Override
	public void preStart() {
		try {
			super.preStart();
			Map<String, ActorRef> actorMap = new HashMap<String, ActorRef>();			
			logger.info("[InquiryMaster] Starting InquiryHandler..");
			inquiryHandler = getContext().actorOf(new RoundRobinPool(actorMap.size() * Integer.valueOf(as.getInquiryEachActorWorker())).props(Props.create(InquiryHandler.class, adapterBillerConfiguration, system, actorMap)), "InquiryHandler");
			
			logger.info("[InquiryMaster] Starting InquiryConsumer..");
			inquiryConsumer = getContext().actorOf(Props.create(InquiryConsumer.class, inquiryHandler, as.getInquiryConsumerUrl()), "InquiryConsumer");
		} catch (Exception e) {
			String err = "[InquiryMaster] error : " + e.getMessage() + ", Trace : " + e.getStackTrace()[0];
			if (err.length() > 499) {
				err = err.substring(0, 499);
			}
			logger.error(err);
			e.printStackTrace();
		}		
	}
}
