package com.glonation.biller.actors.advice;

import java.util.HashMap;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glonation.biller.AdapterSettings;
import com.glonation.biller.actors.purchase.PurchaseSarindo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;

public class AdviceMaster extends UntypedActor {
	
	private static final Logger logger = LoggerFactory.getLogger(AdviceMaster.class);
	private AdapterSettings as;
	private Map<String, Map<String, Map<String, Object>>> adapterBillerConfiguration;
	private ActorSystem system;
	private ActorRef adviceHandler;
	private ActorRef adviceConsumer;
	private ActorRef adviceSarindo;
	private ActorRef adviceXlink;
	private ActorRef adviceKisel;
	public AdviceMaster (AdapterSettings as,
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
			
			logger.info("[PurchaseMaster] Starting ADVICESARINDO..");
			adviceSarindo = getContext().actorOf(new RoundRobinPool(Integer.valueOf(as.getPurchaseEachActorWorker())).props(Props.create(AdviceSarindo.class,adapterBillerConfiguration, system, as)), "AdviceSarindo");
			
			logger.info("[PurchaseMaster] Starting ADVICEXLINK..");
			adviceXlink = getContext().actorOf(new RoundRobinPool(Integer.valueOf(as.getPurchaseEachActorWorker())).props(Props.create(AdviceXlink.class,adapterBillerConfiguration, system, as)), "AdviceXlink");
			
			logger.info("[PurchaseMaster] Starting ADVICEKISEL..");
			adviceKisel = getContext().actorOf(new RoundRobinPool(Integer.valueOf(as.getPurchaseEachActorWorker())).props(Props.create(AdviceKisel.class,adapterBillerConfiguration, system, as)), "AdviceKisel");

			actorMap.put("sarindo", adviceSarindo);
			actorMap.put("xlink", adviceXlink);
			actorMap.put("kisel", adviceKisel);
			
			logger.info("[AdviceMaster] Starting PurchaseHandler..");
			adviceHandler = getContext().actorOf(new RoundRobinPool(actorMap.size() * Integer.valueOf(as.getAdviceEachActorWorker())).props(Props.create(AdviceHandler.class, adapterBillerConfiguration, system, actorMap)), "AdviceHandler");
			
			logger.info("[AdviceMaster] Starting PurchaseConsumer..");
			adviceConsumer = getContext().actorOf(Props.create(AdviceConsumer.class, adviceHandler, as.getAdviceConsumerUrl()), "AdviceConsumer");
		} catch (Exception e) {
			String err = "[AdviceMaster] error : " + e.getMessage() + ", Trace : " + e.getStackTrace()[0];
			if (err.length() > 499) {
				err = err.substring(0, 499);
			}
			logger.error(err);
		}
	}
}