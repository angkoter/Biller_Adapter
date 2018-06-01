package com.glonation.biller;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdapterSettings {
	
	private static final Logger logger = LoggerFactory.getLogger(AdapterSettings.class);
	
	private static AdapterSettings as;

	
	private String appId;
	private String statusAlive;
	private String statusOff;
	private String remarkAlive;
	private String remarkOff;
	private String monitoringTypeStatus;
	private String monitoringTypeError;
	private String monitorStatusUrl;
	private String monitorErrorUrl;
	private String clientId;
	private String systemId;
	private String inquiryConsumerUrl;
	private String inquiryProducerUrl;
	private String purchaseConsumerUrl;
	private String purchaseProducerUrl;
	private String adviceConsumerUrl;
	private String certificateName;
	private String certificatePass;
	
	//Database
	private String dbName;
	private String dbUrl;
	private String dbPassword;
	private String dbUser;
	private String dbTableAdapterBillerConfiguration;
	
	private String inquiryEachActorWorker;
	private String purchaseEachActorWorker;
	private String adviceEachActorWorker;
	private String billerHitTimeout;
	
	
	public static final synchronized AdapterSettings getAdapterSettings() {
		if (null == as) {
			as = new AdapterSettings();
		}
		return as;
	}
	
	public AdapterSettings () {
		readConfig();
	}
	
	public void readConfig () {
		Configuration config = null;
		try {
			config = new PropertiesConfiguration("biller_adapter.properties");
			logger.info("[AdapterSettings] biller_adapter.properties is successfully loaded..");
		} catch (ConfigurationException e) {
			logger.info("[AdapterSettings] Failed to load biller_adapter.properties.."+e);
		}
		setAppId(config.getString("app_id"));
		setStatusAlive(config.getString("status_alive"));
		setStatusOff(config.getString("status_off"));
		setRemarkAlive(config.getString("remark_alive"));
		setRemarkOff(config.getString("remark_off"));
		setMonitoringTypeStatus(config.getString("monitoring_type_status"));
		setMonitoringTypeError(config.getString("monitoring_type_error"));
		setMonitorStatusUrl(config.getString("monitor_status_url"));
		setMonitorErrorUrl(config.getString("monitor_error_url"));
		setClientId(config.getString("client_id"));
		setSystemId(config.getString("system_id"));
		setInquiryConsumerUrl(config.getString("inquiry_consumer_url"));
		setInquiryProducerUrl(config.getString("inquiry_producer_url"));
		setPurchaseConsumerUrl(config.getString("purchase_consumer_url"));
		setPurchaseProducerUrl(config.getString("purchase_producer_url"));
		setDbUrl(config.getString("db_url"));
		setDbName(config.getString("db_name"));
		setDbUser(config.getString("db_user"));
		setPassword(config.getString("db_password"));
		setDbTableAdapterBillerConfiguration(config.getString("db_table_adapter_biller_configuration"));
		setAdviceConsumerUrl(config.getString("advice_consumer_url"));
		setCertificateName(config.getString("certificateName"));
		setCertificatePass(config.getString("certificatePass"));
		
		setInquiryEachActorWorker(config.getString("inquiry_each_actor_worker"));
		setPurchaseEachActorWorker(config.getString("purchase_each_actor_worker"));
		setAdviceEachActorWorker(config.getString("advice_each_actor_worker"));
		setBillerHitTimeout(config.getString("biller_hit_timeout"));
	}
	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getStatusAlive() {
		return statusAlive;
	}
	public void setStatusAlive(String statusAlive) {
		this.statusAlive = statusAlive;
	}
	public String getStatusOff() {
		return statusOff;
	}
	public void setStatusOff(String statusOff) {
		this.statusOff = statusOff;
	}

	public String getRemarkAlive() {
		return remarkAlive;
	}

	public void setRemarkAlive(String remarkAlive) {
		this.remarkAlive = remarkAlive;
	}

	public String getRemarkOff() {
		return remarkOff;
	}

	public void setRemarkOff(String remarkOff) {
		this.remarkOff = remarkOff;
	}

	public String getMonitoringTypeStatus() {
		return monitoringTypeStatus;
	}

	public void setMonitoringTypeStatus(String monitoringTypeStatus) {
		this.monitoringTypeStatus = monitoringTypeStatus;
	}

	public String getMonitoringTypeError() {
		return monitoringTypeError;
	}

	public void setMonitoringTypeError(String monitoringTypeError) {
		this.monitoringTypeError = monitoringTypeError;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getMonitorStatusUrl() {
		return monitorStatusUrl;
	}

	public void setMonitorStatusUrl(String monitorStatusUrl) {
		this.monitorStatusUrl = monitorStatusUrl;
	}

	public String getMonitorErrorUrl() {
		return monitorErrorUrl;
	}

	public void setMonitorErrorUrl(String monitorErrorUrl) {
		this.monitorErrorUrl = monitorErrorUrl;
	}

	public String getInquiryConsumerUrl() {
		return inquiryConsumerUrl;
	}

	public void setInquiryConsumerUrl(String inquiryConsumerUrl) {
		this.inquiryConsumerUrl = inquiryConsumerUrl;
	}

	public String getInquiryProducerUrl() {
		return inquiryProducerUrl;
	}

	public void setInquiryProducerUrl(String inquiryProducerUrl) {
		this.inquiryProducerUrl = inquiryProducerUrl;
	}

	public String getPurchaseConsumerUrl() {
		return purchaseConsumerUrl;
	}

	public void setPurchaseConsumerUrl(String purchaseConsumerUrl) {
		this.purchaseConsumerUrl = purchaseConsumerUrl;
	}

	public String getPurchaseProducerUrl() {
		return purchaseProducerUrl;
	}

	public void setPurchaseProducerUrl(String purchaseProducerUrl) {
		this.purchaseProducerUrl = purchaseProducerUrl;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbTableAdapterBillerConfiguration() {
		return dbTableAdapterBillerConfiguration;
	}

	public void setDbTableAdapterBillerConfiguration(
			String dbTableAdapterBillerConfiguration) {
		this.dbTableAdapterBillerConfiguration = dbTableAdapterBillerConfiguration;
	}

	public String getAdviceConsumerUrl() {
		return adviceConsumerUrl;
	}

	public void setAdviceConsumerUrl(String adviceConsumerUrl) {
		this.adviceConsumerUrl = adviceConsumerUrl;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificatePass() {
		return certificatePass;
	}

	public void setCertificatePass(String certificatePass) {
		this.certificatePass = certificatePass;
	}

	public String getInquiryEachActorWorker() {
		return inquiryEachActorWorker;
	}

	public void setInquiryEachActorWorker(String inquiryEachActorWorker) {
		this.inquiryEachActorWorker = inquiryEachActorWorker;
	}

	public String getPurchaseEachActorWorker() {
		return purchaseEachActorWorker;
	}

	public void setPurchaseEachActorWorker(String purchaseEachActorWorker) {
		this.purchaseEachActorWorker = purchaseEachActorWorker;
	}

	public String getAdviceEachActorWorker() {
		return adviceEachActorWorker;
	}

	public void setAdviceEachActorWorker(String adviceEachActorWorker) {
		this.adviceEachActorWorker = adviceEachActorWorker;
	}

	public String getBillerHitTimeout() {
		return billerHitTimeout;
	}

	public void setBillerHitTimeout(String billerHitTimeout) {
		this.billerHitTimeout = billerHitTimeout;
	}
}
