package com.ericsson.li.http;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.ClientParamsStack;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.ssl.SSLContextBuilder;

public class HttpClientTest2 {

	public static void main(String args[]) {

		test();
	}
	
	public static void test() {
		CloseableHttpClient httpClient = null;
	    try {
	        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
	        sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());

	        HostnameVerifier hostnameVerifierAllowAll = new HostnameVerifier() 
	            {
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            };

	        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build(), hostnameVerifierAllowAll);

	        CredentialsProvider credsProvider = new BasicCredentialsProvider();
	        credsProvider.setCredentials(
	            new AuthScope("192.168.30.34", 8443),
	            new UsernamePasswordCredentials("root", "password"));

	        httpClient = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).setDefaultCredentialsProvider(credsProvider).build();
	        
	        

	        HttpGet httpGet = new HttpGet("https://192.168.30.34:8443/axis/services/getStuff?firstResult=0&maxResults=1000");

	        CloseableHttpResponse response = httpClient.execute(httpGet);

	        int httpStatus = response.getStatusLine().getStatusCode();
	        if (httpStatus >= 200 && httpStatus < 300) { 
	        } else {
	            throw new ClientProtocolException("Unexpected response status: " + httpStatus);
	        }

	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    finally {
	        try {
	            httpClient.close();
	        } catch (IOException ex) {
	            System.out.println("Error while closing the HTTP client: " + ex);
	        }
	    }
	}
	
	

}



