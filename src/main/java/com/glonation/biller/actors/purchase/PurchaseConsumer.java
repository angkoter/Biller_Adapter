package com.glonation.biller.actors.purchase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;

public class PurchaseConsumer extends UntypedConsumerActor {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseConsumer.class);
	private String url;
	private ActorRef purchaseHandler;

	public PurchaseConsumer (ActorRef purchaseHandler, String url) {
		this.url = url;
		this.purchaseHandler = purchaseHandler;
	}
	
	@Override
	public String getEndpointUri() {
		// TODO Auto-generated method stub
		logger.info("[PurchaseConsumer]Start consuming from " + url);
		return "jetty:"+url;
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		// TODO Auto-generated method stub
		if (message instanceof CamelMessage) {
			CamelMessage camelMessage = (CamelMessage) message;
			if (camelMessage.getHeaders().get("CamelHttpMethod").equals("POST")) {
				logger.debug("[PurchaseConsumer]Received message : " + camelMessage.getBodyAs(String.class, getCamelContext()));
				purchaseHandler.forward(camelMessage,getContext());
			}
			else {
				getSender().tell("Please use post method", getSelf());
			}
		}
		else {
			String logString = "[PurchaseConsumer]Received unknown message : " +message.toString();
			logger.debug(logString);
			if (logString.length() > 500) {
				String m = logString.toString().substring(0, 499);
			}
			getSender().tell("[PurchaseConsumer] Error : Unknown message", getSelf());
		}
	}
}
