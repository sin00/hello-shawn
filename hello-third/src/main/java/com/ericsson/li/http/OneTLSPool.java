package com.ericsson.li.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class OneTLSPool {
	public static CloseableHttpClient httpclient;
	public static final String KEY_STORE_TRUST_PATH = "D://https//ca//cl.jks"; // truststore的路径
	public static final String KEY_STORE_TYPE_JKS = "jks"; // truststore的类型
	private static final String KEY_STORE_TRUST_PASSWORD = "123456"; // truststore的密码
	// 获得池化得HttpClient

	static {
		SSLContext sslcontext = null;
		try {
			// 设置truststore
			KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_JKS);
			InputStream tsIn = new FileInputStream(new File(KEY_STORE_TRUST_PATH));
			try {
				trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());
			} finally {
				try {
					tsIn.close();
				} catch (Exception ignore) {
				}
			}
			sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
			// 解决jdk7的ssl的自签名会有问题的bug，如果不是jdk7，则下面的代码可以没有
			// bug地址：http://bugs.java.com/bugdatabase/view_bug.do?bug_id=7018897
			X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null; // return new
									// java.security.cert.X509Certificate[0];
				}
			};
			sslcontext.init(null, new TrustManager[] { xtm }, null);
			// 解决bug结束
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 客户端支持TLSV1，TLSV2,TLSV3这三个版本
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
				new String[] { "TLSv1", "TLSv2", "TLSv3" }, null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());// 客户端验证服务器身份的策略

		// Create a registry of custom connection socket factories for supported
		// protocol schemes.
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		// Configure total max or per route limits for persistent connections
		// that can be kept in the pool or leased by the connection manager.
		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(10);
		// 个性化设置某个url的连接
		connManager.setMaxPerRoute(new HttpRoute(new HttpHost("www.y.com", 80)), 20);
		httpclient = HttpClients.custom().setConnectionManager(connManager).build();

	}

	/**
	 * 单向验证且服务端的证书可信
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void oneWayAuthorizationAccepted() throws ClientProtocolException, IOException {
		// Execution context can be customized locally.
		HttpClientContext context = HttpClientContext.create();
		HttpGet httpget = new HttpGet("https://www.yunzhu.com:8443");
		// 设置请求的配置
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000).build();
		httpget.setConfig(requestConfig);

		System.out.println("executing request " + httpget.getURI());
		CloseableHttpResponse response = httpclient.execute(httpget, context);
		try {
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			System.out.println(EntityUtils.toString(response.getEntity()));
			System.out.println("----------------------------------------");

			// Once the request has been executed the local context can
			// be used to examine updated state and various objects affected
			// by the request execution.

			// Last executed request
			context.getRequest();
			// Execution route
			context.getHttpRoute();
			// Target auth state
			context.getTargetAuthState();
			// Proxy auth state
			context.getTargetAuthState();
			// Cookie origin
			context.getCookieOrigin();
			// Cookie spec used
			context.getCookieSpec();
			// User security token
			context.getUserToken();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] a) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
			CertificateException, IOException {
		oneWayAuthorizationAccepted();
	}
}
