package com.glonation.biller.actors.inquiry;

import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.Function1;


import akka.actor.ActorRef;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;

public class InquiryConsumer extends UntypedConsumerActor {

	private static final Logger logger = LoggerFactory.getLogger(InquiryConsumer.class);
	private ActorRef inquiryHandler;
	private String url;
	
	public InquiryConsumer (ActorRef inquiryHandler, String url) {
		this.inquiryHandler = inquiryHandler;
		this.url = url;
	}
	
	@Override
	public String getEndpointUri() {
		// TODO Auto-generated method stub
		logger.info("[InquiryConsumer] Start consuming from " + url);
		return "jetty:"+url;
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		// TODO Auto-generated method stub
		if (message instanceof CamelMessage) {
			CamelMessage camelMessage = (CamelMessage)message;
			if (camelMessage.getHeaders().get("CamelHttpMethod").equals("POST")) {
				logger.debug("[InquiryConsumer]Message Received : " + camelMessage.getBodyAs(String.class, getCamelContext()));
				inquiryHandler.forward(camelMessage, getContext());
			}
			else {
				getSender().tell("Please use POST Method", getSelf());
			}
		}
		else {
			String logString = "[InquiryConsumer]Received unknown message : " +message.toString();
			logger.debug(logString);
			if (logString.length() > 500) {
				String m = logString.toString().substring(0, 499);
			}
			getSender().tell("[InquiryConsumer] Error : Unknown message", getSelf());
		}
	}

	@Override
	public Function1<RouteDefinition, ProcessorDefinition<?>> onRouteDefinition() {
		// TODO Auto-generated method stub
		return super.onRouteDefinition();
	}
	
}
