package com.glonation.biller.actors.purchase;

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

public class PurchaseMaster extends UntypedActor {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseMaster.class);
	private AdapterSettings as;
	private ActorRef purchaseHandler;
	private Map<String, Map<String, Map<String, Object>>> adapterBillerConfiguration;
	private ActorSystem system;
	private ActorRef purchaseConsumer;
	private ActorRef purchaseSarindo;
	private ActorRef purchaseXlink;
	
	public PurchaseMaster (AdapterSettings as,
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
			Map<String, ActorRef> actorMap = new HashMap<String, ActorRef>();
			
			logger.info("[PurchaseMaster] Starting PURCHASESARINDO..");
			purchaseSarindo = getContext().actorOf(new RoundRobinPool(Integer.valueOf(as.getPurchaseEachActorWorker())).props(Props.create(PurchaseSarindo.class,adapterBillerConfiguration, system, as)), "PurchaseSarindo");
			logger.info("[PurchaseMaster] Starting PURCHASEXLINK..");
			purchaseXlink = getContext().actorOf(new RoundRobinPool(Integer.valueOf(as.getPurchaseEachActorWorker())).props(Props.create(PurchaseXlink.class,adapterBillerConfiguration, system, as)), "PurchaseXlink");

			actorMap.put("sarindo", purchaseSarindo);
			actorMap.put("xlink", purchaseXlink);
			
			logger.info("[PurchaseMaster] Starting PurchaseHandler..");
			purchaseHandler = getContext().actorOf(new RoundRobinPool(actorMap.size() * Integer.valueOf(as.getPurchaseEachActorWorker())).props(Props.create(PurchaseHandler.class,  adapterBillerConfiguration, system, actorMap)), "PurchaseHandler");
			
			logger.info("[PurchaseMaster] Starting PurchaseConsumer..");
			purchaseConsumer = getContext().actorOf(Props.create(PurchaseConsumer.class, purchaseHandler, as.getPurchaseConsumerUrl()), "PurchaseConsumer");
		}
		 catch (Exception e) {
			String err = "[PurchaseMaster] error : " + e.getMessage() + ", Trace : " + e.getStackTrace()[0];
			if (err.length() > 499) {
				err = err.substring(0, 499);
			}
			System.out.println("err : " + err);
			logger.error(err);
			e.printStackTrace();
		}
	}
}
