package com.glonation.biller.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import com.glonation.biller.actors.purchase.PurchaseSarindo;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Hitter {
	private static final Logger logger = LoggerFactory.getLogger(Hitter.class);
	private String TAG = "[" + this.getClass().getSimpleName() + "] ";

	public SSLSocketFactory getCertHttps() {
		// Create a trust manager that does not validate certificate chains
		try {
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					// TODO Auto-generated method stub

				}
			} };

			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
			return sslSocketFactory;
		} catch (Exception e) {
			logger.error(TAG + "Fail to get reaponse : " + e.getMessage());
			return null;
		}

	}

	public String hitBillerGET(String urlBiller, String request) {
		String responseBiller = null;

		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			okHttpClient.setReadTimeout(90, TimeUnit.SECONDS);
			okHttpClient.setConnectTimeout(90, TimeUnit.SECONDS);
			okHttpClient.setSslSocketFactory(getCertHttps());
			okHttpClient.setHostnameVerifier(new HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {
					// TODO Auto-generated method stub
					return true;
				}
			});
			Request r = new Request.Builder().url(urlBiller + "?" + request).get().build();
//			Request r = new Request.Builder().url(urlBiller).get().build();
			Response response;

			response = okHttpClient.newCall(r).execute();
			responseBiller = response.body().string();
			logger.debug(TAG + "hit to : "+urlBiller+"response code : " + response.code());

		} catch (IOException e) {
			logger.error(TAG + "Fail to get reaponse : " + e.getMessage());
		}

		return responseBiller;
	}

	public String hitBillerPOST(String urlBiller, String request, String ContentType) {
		String responseBiller = null;
		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			okHttpClient.setReadTimeout(90, TimeUnit.SECONDS);
			okHttpClient.setConnectTimeout(90, TimeUnit.SECONDS);
			okHttpClient.setSslSocketFactory(getCertHttps());
			okHttpClient.setHostnameVerifier(new HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {
					// TODO Auto-generated method stub
					return true;
				}
			});
			RequestBody body = RequestBody.create(MediaType.parse(ContentType), request);
			Request r = new Request.Builder().url(urlBiller).post(body).build();
			Response response = okHttpClient.newCall(r).execute();
			responseBiller = response.body().string();
			logger.debug(TAG + "hit to : "+urlBiller+" response code : " + response.code());

		} catch (Exception e) {
			logger.error(TAG + "Fail to get reaponse : " + e.getMessage());
		}
		return responseBiller;
	}

}
