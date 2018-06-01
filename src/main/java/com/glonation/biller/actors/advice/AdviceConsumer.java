package com.glonation.biller.actors.advice;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;


import akka.actor.ActorRef;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;

public class AdviceConsumer extends UntypedConsumerActor {

	private static final Logger logger = LoggerFactory.getLogger(AdviceConsumer.class);
	private String url;
	private ActorRef adviceHandler;

	public AdviceConsumer (ActorRef adviceHandler, String url) {
		this.url = url;
		this.adviceHandler = adviceHandler;
	}
	
	@Override
	public String getEndpointUri() {
		// TODO Auto-generated method stub
		logger.info("[AdviceConsumer] Start consuming from " + url);
		return "jetty:"+url;
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		// TODO Auto-generated method stub
		if (message instanceof CamelMessage) {
			CamelMessage camelMessage = (CamelMessage) message;
			if (camelMessage.getHeaders().get("CamelHttpMethod").equals("POST")) {
				logger.debug("[AdviceConsumer] Received message : " + camelMessage.getBodyAs(String.class, getCamelContext()));
				adviceHandler.forward(camelMessage,getContext());
			}
			else {
				getSender().tell("Please use post method", getSelf());
			}
		}
		else {
			String logString = "[AdviceConsumer] Received unknown message : " +message.toString();
			logger.debug(logString);
			if (logString.length() > 500) {
				String m = logString.toString().substring(0, 499);
			}
			getSender().tell("[AdviceConsumer] Error : Unknown message", getSelf());
		}
	}
}
