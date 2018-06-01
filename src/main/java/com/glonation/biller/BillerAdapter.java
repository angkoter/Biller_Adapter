package com.glonation.biller;

import static akka.pattern.Patterns.gracefulStop;


import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import com.glonation.biller.AdapterSettings;
import com.glonation.biller.BillerAdapter;
import com.glonation.biller.actors.MasterActor;
import com.glonation.biller.utils.LoadParameter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.kernel.Bootable;

@SuppressWarnings("deprecation")
public class BillerAdapter implements Bootable{

	private static final Logger logger = LoggerFactory.getLogger(BillerAdapter.class);
	private static final int LIMIT_STOP_DURATION = 2;
	private static ActorSystem system;
	private ActorRef master;
	private AdapterSettings as;
//	private RunMonitoring rm;
//	private Map<String, Map<String, Map<String, Object>>> parameters;
	private Map<String, Map<String, Map<String, Object>>> adapterBillerConfiguration;
	
	static {
		system = ActorSystem.create("Biller-Adapter");
	}
	@Override
	public void startup() {
		logger.info("[BillerAdapter] Reading Adapter Settings..");
		as = AdapterSettings.getAdapterSettings();
//		rm = new RunMonitoring();
//		rm.setClientId(as.getClientId());
//		rm.setUrlStatus(as.getMonitorStatusUrl());
//		rm.setUrlError(as.getMonitorErrorUrl());
//		rm.setApplicationid(as.getAppId());
//		rm.setSystemId(as.getSystemId());
//		rm.setMonitoringTypeStatus(as.getMonitoringTypeStatus());
//		rm.setMonitoringTypeError(as.getMonitoringTypeError());
		
		logger.info("[BillerAdapter]Loading Parameter Data from DB..");
		try {
			LoadParameter lp = new LoadParameter(as.getDbUrl(), as.getDbName(), as.getDbUser(), as.getDbPassword());
			LoadParameter lp2 = new LoadParameter(as.getDbUrl(), as.getDbName(), as.getDbUser(), as.getDbPassword());
//			parameters = lp.getAdapterConfiguration2("select * from "+as.getDbTableAdapterBillerMappingParam() + " ORDER BY IdAdapterBillerConfiguration, ParamType ASC",
//								"2", "3", "4", "5");
			adapterBillerConfiguration = lp2.getAdapterConfiguration("select * from "+as.getDbTableAdapterBillerConfiguration(),
								"2", "4", "6");
//			if (parameters.size() > 0 && adapterBillerConfiguration.size() > 0) {
//				logger.info("[BillerAdapter] DB loaded successfully.." + "adapterBillerConfiguration : " + adapterBillerConfiguration);
//				logger.info("[BillerAdapter] DB loaded successfully.." + "parameters : " + parameters);
//			}
			
//			for (Map.Entry<String, Object> entry : checksumComponent.entrySet()) {
//				System.out.println("checksum key : " + entry.getKey()+ ", value : " +entry.getValue());
//			}
			
//			for (Map.Entry<String, Map<String, Map<String, Object>>> entry : parameters.entrySet()) {
//				System.out.println("parameters key : " + entry.getKey()+ ", value : " +entry.getValue());
//			}
			
			/*
			for (Map.Entry<String, Map<String, Map<String, Object>>> entry : parameterMB.entrySet()) {
				for (Map.Entry<String, Map<String, Object>> entry2 : entry.getValue().entrySet()) {
					System.out.println("parameterMB key entry : " + entry2.getKey()+ ", value : " +entry2.getValue());
				}
			}*/
		} catch (Exception e) {
			logger.info("[BillerAdapter]Failed to load Data from DB : " + e.toString());
		}
		
		logger.info("[BillerAdapter] Biller-Adapter-System is starting up..");
		
		master = system.actorOf(Props.create(MasterActor.class, as, adapterBillerConfiguration, system)
				.withDispatcher("akka.actor.mailbox-dispatcher"), "MasterActor");
//		master.tell("start", ActorRef.noSender());
		
//		logger.info("[BillerAdapter] Reporting status alive to Agais..");
//		int code = rm.sendStatusByGETMethod(as.getStatusAlive(), as.getRemarkAlive());
//		logger.info("[BillerAdapter] Agais ack code : "+code);
		
		logger.info("[BillerAdapter]Loading data from DB..");
		

	}

	@Override
	public void shutdown() {
		try {
			logger.info("[BillerAdapter] Shutting down Merchant-Biller-Adapter-System..");
			Future<Boolean> stopped = gracefulStop(master, Duration.create(LIMIT_STOP_DURATION, TimeUnit.MINUTES));
			Await.result(stopped, Duration.create(LIMIT_STOP_DURATION, TimeUnit.MINUTES));
//			logger.info("[BillerAdapter] Reporting status off to Agais..");
//			int code = rm.sendStatusByGETMethod(as.getStatusOff(), as.getRemarkOff());
//			logger.info("[BillerAdapter] Agais ack code : "+code);
		} catch (Exception ex) {
			logger.error("[BillerAdapter] Failed to shutdown properly.."+ex);
		}
		system.shutdown();
	}
}