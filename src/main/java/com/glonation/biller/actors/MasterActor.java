package com.glonation.biller.actors;

import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glonation.biller.AdapterSettings;
import com.glonation.biller.actors.MasterActor;
import com.glonation.biller.actors.advice.AdviceMaster;
import com.glonation.biller.actors.inquiry.InquiryMaster;
import com.glonation.biller.actors.purchase.PurchaseMaster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class MasterActor extends UntypedActor {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterActor.class);
	
	private ActorRef inquiryMaster;
	private AdapterSettings as;
	private int maxRetry = 4;
	private ActorRef purchaseMaster;
	private Map<String, Map<String, Map<String, Object>>> adapterBillerConfiguration;
	private ActorSystem system;

	private ActorRef adviceMaster;
	
	public MasterActor (
						AdapterSettings as,
						
						Map<String, Map<String, Map<String, Object>>> adapterBillerConfiguration,
						ActorSystem system) {
		
		this.as = as;
		this.adapterBillerConfiguration = adapterBillerConfiguration;
		this.system = system;
		
}

	@Override
	public void onReceive(Object arg0) throws Throwable {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStop() throws Exception {
		// TODO Auto-generated method stub
		super.postStop();
	}

	@Override
	public void preStart() {
		// TODO Auto-generated method stub
		try {
			super.preStart();
			logger.info("[MasterActor] Starting Inquiry Master..");
			inquiryMaster = getContext().actorOf(Props.create(InquiryMaster.class, as,  adapterBillerConfiguration, system), "InquiryMaster");
			
			logger.info("[MasterActor] Starting Purchase Master..");
			purchaseMaster = getContext().actorOf(Props.create(PurchaseMaster.class, as, adapterBillerConfiguration, system), "PurchaseMaster");
			
			logger.info("[MasterActor] Starting Advice Master..");
			adviceMaster = getContext().actorOf(Props.create(AdviceMaster.class, as, adapterBillerConfiguration, system), "AdviceMaster");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String err = "[MasterActor] error : " + e.getMessage() + ", Trace : " + e.getStackTrace()[0];
			if (err.length() > 499) {
				err = err.substring(0, 499);
			}
			logger.error(err);
//			rm.sendErrorByGETMethod(err);
		}
	}
	
}
