package com.ericsson.li.http;

import java.io.BufferedInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.StringWriter;  
import java.io.UnsupportedEncodingException;  
import java.net.SocketTimeoutException;  
import java.net.URLEncoder;  
import java.nio.charset.CodingErrorAction;  
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;  
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Map;  
  
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;  
import org.apache.http.Header;  
import org.apache.http.HeaderIterator;  
import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.config.RequestConfig;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.config.ConnectionConfig;  
import org.apache.http.config.MessageConstraints;  
import org.apache.http.config.Registry;  
import org.apache.http.config.RegistryBuilder;  
import org.apache.http.config.SocketConfig;  
import org.apache.http.conn.ConnectTimeoutException;  
import org.apache.http.conn.socket.ConnectionSocketFactory;  
import org.apache.http.conn.socket.PlainConnectionSocketFactory;  
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;  
import org.apache.http.conn.ssl.SSLContextBuilder;  
import org.apache.http.conn.ssl.TrustStrategy;  
import org.apache.http.entity.StringEntity;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClients;  
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;  
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;  
import org.dom4j.Document;  
import org.dom4j.DocumentHelper;  
import org.dom4j.io.OutputFormat;  
import org.dom4j.io.XMLWriter;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
/** 
 * HTTP协议请求 
 * @author admin 
 * 
 */  
public final class HTTPUtil {  
  
    private final static Logger logger = LoggerFactory.getLogger(HTTPUtil.class);  
      
    private static final int REQUEST_TIMEOUT = 30 * 1000; // 设置请求超时10秒钟  
    private static final int TIMEOUT         = 60 * 1000; // 连接超时时间  
    private static final int SO_TIMEOUT      = 60 * 1000; // 数据传输超时  
    private static final String CHARSET      = "UTF-8";  
      
    private  PoolingHttpClientConnectionManager connManager = null;  
    private  CloseableHttpClient httpClient = null;  
  
    private void init() {
        try  
        {  
            // SSLContext  
            SSLContextBuilder sslContextbuilder = new SSLContextBuilder();  
            sslContextbuilder.useTLS();  
            SSLContext sslContext = sslContextbuilder.loadTrustMaterial(null, new TrustStrategy() {  
  
                // 信任所有  
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException  
                {  
                    return true;  
                }  
  
            }).build();  
              
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()  
                    .register("http", PlainConnectionSocketFactory.INSTANCE)  
                    .register("https", new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)).build();  
              
            // Create ConnectionManager  
            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
  
            // Create socket configuration  
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();  
            connManager.setDefaultSocketConfig(socketConfig);  
  
            // Create message constraints  
            MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();  
  
            // Create connection configuration  
            ConnectionConfig connectionConfig = ConnectionConfig.custom()  
                    .setMalformedInputAction(CodingErrorAction.IGNORE)  
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)  
                    .setCharset(Consts.UTF_8)  
                    .setMessageConstraints(messageConstraints).build();  
  
            connManager.setDefaultConnectionConfig(connectionConfig);  
            connManager.setMaxTotal(200);  
            connManager.setDefaultMaxPerRoute(20);  
              
            // Create httpClient  
            httpClient = HttpClients.custom().disableRedirectHandling().setConnectionManager(connManager).build();  
        }  
        catch (KeyManagementException e)  
        {  
            logger.error("KeyManagementException", e);  
        }  
        catch (NoSuchAlgorithmException e)  
        {  
            logger.error("NoSuchAlgorithmException", e);  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
  
    }  
    
    public static void main(String[] args) {
    	//String uri = "https://54.187.209.172:9444/api/v1/sign/certificates";
    	//String uri = "https://54.187.209.172:9444/api/v1/certificate/status?begin=1480580096&end=1481085331";
    	
    	String uri = "https://54.203.0.240:9444/api/v1/certificate/status?begin=1481068800&end=1481155199";
    	HTTPUtil httpUtild = new HTTPUtil();
    	httpUtild.init();
    	uri = "http://121.43.187.244:80/integration/xml.do?_serviceid=98501&_method=queryNtBbDealer&_service_password=WBSAgciAfDnRdabm&_response_type=json";
    	httpUtild.doGet(uri);
    	uri = "http://121.43.187.244:80/integration/xml.do?_serviceid=98537&_method=getStationMessage&_service_password=WBSAgciAfDnRdabm&_response_type=json";
    	httpUtild.doGet(uri);
    }
      
    /** 
     * 指定参数名GET方式请求数据 
     * @param url 
     * @param paramsMap QueryString 
     * @return 
     */  
    public  String doGet(String url, Map<String, String> paramsMap)  
    {  
        return doGet(invokeUrl(url, paramsMap));  
    }  
    /** 
     * GET方式请求数据 
     * @param url 
     */  
    public  String doGet(String url)  
    {  
        HttpGet httpGet = new HttpGet(url);  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(SO_TIMEOUT)  
                .setConnectTimeout(TIMEOUT)  
                .setConnectionRequestTimeout(REQUEST_TIMEOUT).build();  
        httpGet.setConfig(requestConfig);  
          
        long responseLength = 0; // 响应长度  
        String responseContent = null; // 响应内容  
        String strRep = null;  
        try {  
            // 执行get请求  
            HttpResponse httpResponse = httpClient.execute(httpGet);  
              
            // 头信息  
            printHeaders(httpResponse);  
  
            // 获取响应消息实体  
            HttpEntity entity = httpResponse.getEntity();  
            if (entity != null)   
            {  
                responseLength = entity.getContentLength();  
                responseContent = EntityUtils.toString(entity, CHARSET);//不能重复调用此方法，IO流已关闭。  
                  
                System.err.println("内容编码: " + entity.getContentEncoding());  
                System.err.println("请求地址: " + httpGet.getURI());  
                System.err.println("响应状态: " + httpResponse.getStatusLine());  
                System.err.println("响应长度: " + responseLength);  
                System.err.println("响应内容: \r\n" + responseContent);  
                //System.err.println("响应内容: \r\n" + responseContent.substring(0, 200)); 
                  
                // 获取HTTP响应的状态码  
                int statusCode = httpResponse.getStatusLine().getStatusCode();  
                if (statusCode == HttpStatus.SC_OK)  
                {  
                    strRep = responseContent; // EntityUtils.toString(httpResponse.getEntity());  
                }  
                  
                // Consume response content  
                EntityUtils.consume(entity);  
                // Do not need the rest  
                httpGet.abort();  
            }  
        }   
        catch (ClientProtocolException e)  
        {  
            logger.error("ClientProtocolException", e);  
            e.printStackTrace();  
        }  
        catch (UnsupportedEncodingException e) {    
            logger.error("UnsupportedEncodingException", e);  
            e.printStackTrace();   
        }  
        catch (ConnectTimeoutException e) {  
            logger.error("ConnectTimeoutException", e);  
            e.printStackTrace();  
        }  
        catch (SocketTimeoutException e) {  
            logger.error("SocketTimeoutException", e);  
            e.printStackTrace();  
        }  
        catch (Exception e)  
        {  
            logger.error("Exception", e);  
        } finally {  
            httpGet.releaseConnection();  
        }  
          
        return strRep;  
    }  
      
    /** 
     * 不指定参数名的方式来POST数据 
     * @param url 
     * @param jsonXMLString 
     * @return 
     */  
    public String doPost(String url, String jsonXMLString)  
    {  
        return doPost(url, null, jsonXMLString);  
    }  
    /** 
     * 指定参数名POST方式请求数据 
     * @param url 
     */  
    public  String doPost(String url, Map<String, String> paramsMap)  
    {  
        return doPost(url, paramsMap, null);  
    }  
    private  String doPost(String url, Map<String, String> paramsMap, String jsonXMLString)  
    {  
        HttpPost httpPost = new HttpPost(url);  
        httpPost.setHeader("Content-type", "text/xml; charset=gbk");  
  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(SO_TIMEOUT)  
                .setConnectTimeout(TIMEOUT)  
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)  
                .setExpectContinueEnabled(false).build();  
  
        httpPost.setConfig(requestConfig);// RequestConfig.DEFAULT  
          
        long responseLength = 0; // 响应长度  
        String responseContent = null; // 响应内容  
        String strRep = null;  
        try {  
            if(paramsMap !=null && jsonXMLString == null)  
            {  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(getParamsList(paramsMap), CHARSET);    
                httpPost.setEntity(entity);  
            }  
            else  
            {  
                httpPost.setEntity(new StringEntity(jsonXMLString, CHARSET));  
            }  
              
            // 执行post请求  
            HttpResponse httpResponse = httpClient.execute(httpPost);  
              
            // 头信息  
            printHeaders(httpResponse);  
              
            // 获取响应消息实体  
            HttpEntity entityRep = httpResponse.getEntity();  
            if (entityRep != null)   
            {  
                responseLength = entityRep.getContentLength();  
                responseContent = EntityUtils.toString(httpResponse.getEntity(), CHARSET);  
                  
                // byte[] bytes = EntityUtils.toByteArray(entityRep);  
                  
                System.err.println("内容编码: " + entityRep.getContentEncoding());  
                System.err.println("请求地址: " + httpPost.getURI());  
                System.err.println("响应状态: " + httpResponse.getStatusLine());  
                System.err.println("响应长度: " + responseLength);  
                System.err.println("响应内容: \r\n" + responseContent);  
                  
                // 获取HTTP响应的状态码  
                int statusCode = httpResponse.getStatusLine().getStatusCode();  
                if (statusCode == HttpStatus.SC_OK)  
                {  
                    strRep = responseContent; // EntityUtils.toString(httpResponse.getEntity());  
                      
                    // 格式化输出XML  
                    System.err.println("-----------------------------------------");  
                    System.err.println(formatXML(responseContent));  
                    System.err.println("-----------------------------------------");  
                }  
                else if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY)  
                         || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) || (statusCode == HttpStatus.SC_SEE_OTHER)  
                         || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT))  
                {  
                    // 重定向处理，获得跳转的网址  
                    Header locationHeader = httpResponse.getFirstHeader("Location");  
                    if(locationHeader != null)  
                    {  
                        String successUrl = locationHeader.getValue();  
                        System.out.println(successUrl);  
                    }  
                }  
                  
                // Consume response content  
                EntityUtils.consume(entityRep);  
                // Do not need the rest  
                httpPost.abort();  
            }  
        }  
        catch (ClientProtocolException e)  
        {  
            logger.error("ClientProtocolException", e);  
            e.printStackTrace();  
        }  
        catch (UnsupportedEncodingException e) {    
            logger.error("UnsupportedEncodingException", e);  
            e.printStackTrace();   
        }  
        catch (ConnectTimeoutException e) {  
            logger.error("ConnectTimeoutException", e);  
            e.printStackTrace();  
        }  
        catch (SocketTimeoutException e) {  
            logger.error("SocketTimeoutException", e);  
            e.printStackTrace();  
        }  
        catch (Exception e)  
        {  
            logger.error("Exception", e);  
            e.printStackTrace();  
        } finally {  
            httpPost.releaseConnection();  
//            try  
//            {  
//                httpClient.close();  
//            }  
//            catch (IOException e)  
//            {  
//                e.printStackTrace();  
//            }  
        }  
          
        return strRep;  
    }  
      
    // 打印头信息  
    private void printHeaders(HttpResponse httpResponse)  
    {  
        System.out.println("------------------------------");  
        // 头信息  
        HeaderIterator it = httpResponse.headerIterator();  
        while (it.hasNext()) {  
            System.out.println(it.next());  
        }  
        System.out.println("------------------------------");  
    }  
      
    // 读取内容  
    protected String readContent(InputStream in) throws Exception  
    {  
        BufferedInputStream buffer = new BufferedInputStream(in);  
        StringBuilder builder = new StringBuilder();  
        byte[] bytes = new byte[1024];  
        int line = 0;  
        while ((line = buffer.read(bytes)) != -1) {  
            builder.append(new String(bytes, 0, line, CHARSET));  
        }  
          
        return builder.toString();  
    }  
      
    /** 
     * GET方式传参 
     * @param url 
     * @param paramsMap 
     * @return 
     */  
    public String invokeUrl(String url, Map<String, String> paramsMap)  
    {  
        StringBuilder sb = new StringBuilder();  
        sb.append(url);  
        int i = 0;  
        if(paramsMap != null && paramsMap.size()>0)  
        {  
            for (Map.Entry<String, String> entry : paramsMap.entrySet())  
            {  
                if (i == 0 && !url.contains("?"))  
                {  
                    sb.append("?");  
                }  
                else  
                {  
                    sb.append("&");  
                }  
                sb.append(entry.getKey());  
                sb.append("=");  
                String value = entry.getValue();  
                try  
                {  
                    sb.append(URLEncoder.encode(value, CHARSET));  
                }  
                catch (UnsupportedEncodingException e)  
                {  
                    logger.warn("encode http get params error, value is " + value, e);  
                    try  
                    {  
                        sb.append(URLEncoder.encode(value, null));  
                    }  
                    catch (UnsupportedEncodingException e1)  
                    {  
                        e1.printStackTrace();  
                    }  
                }  
  
                i++;  
            }  
        }  
  
        return sb.toString();  
    }  
      
    /** 
     * 将传入的键/值对参数转换为NameValuePair参数集 
     *  
     * @param paramsMap 参数集, 键/值对 
     * @return NameValuePair参数集 
     */  
    private List<NameValuePair> getParamsList(Map<String, String> paramsMap)  
    {  
        if (paramsMap == null || paramsMap.size() == 0)  
        {  
            return null;  
        }  
          
        // 创建参数队列  
        List<NameValuePair> params = new ArrayList<NameValuePair>();  
        for (Map.Entry<String, String> map : paramsMap.entrySet())  
        {  
            params.add(new BasicNameValuePair(map.getKey(), map.getValue()));  
        }  
          
        return params;  
    }  
      
    /** 
     * 格式化XML 
     * @param inputXML 
     * @return 
     * @throws Exception 
     */  
    public String formatXML(String inputXML) throws Exception  
    {  
        Document doc = DocumentHelper.parseText(inputXML);  
        StringWriter out = null;  
        if(doc != null)  
        {  
            try  
            {  
                OutputFormat format = OutputFormat.createPrettyPrint();  
                out = new StringWriter();  
                XMLWriter writer = new XMLWriter(out, format);  
                writer.write(doc);  
                writer.flush();  
            }  
            catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
            finally  
            {  
                out.close();  
            }  
              
            return out.toString();  
        }  
          
        return inputXML;  
    }  
     
    
    
/*	
	public void init(int maxTotal) {
//		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		//httpclient = HttpClients.custom().setConnectionManager(cm).build();

		//SSLContext sslContext = SSLContexts.createSystemDefault();

        
		try {
			//SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			//SSLContext sslContext = SSLContexts.createSystemDefault();
			
			TrustManager truseAllManager = new X509TrustManager() {

				public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// TODO Auto-generated method stub

				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// TODO Auto-generated method stub

				}

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}
			};
			//SSLContext sslContext = SSLContext.getInstance("TLS");  

	        
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
	        trustStore.load(null, null);  
	        
			//SSLContext sslContext = SSLContexts.custom().useProtocol("TLS").loadTrustMaterial(trustStore)
					//loadTrustMaterial(trustStore).build();
	        //SSLContext sslContext = SSLContexts.custom().useProtocol("TLS").build();
	        //SSLContext sslContext = SSLContexts.custom().useProtocol("TLS").build();
			SSLContext sslContext = SSLContexts.custom().useProtocol("TLS").loadTrustMaterial(trustStore, new TrustStrategy(){  
	            //信任所有  
	            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
	                return true;  
	            }}).build(); 
			
	        sslContext.init(null, new TrustManager[]{truseAllManager}, null);  
	        
	        
	        
	        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);//, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER

	        
	        
	        
	        //SSLConnectionSocketFactory sslConnectionSocketFactory =  SSLConnectionSocketFactory.getSocketFactory();
	       // SSLConnectionSocketFactory sslConnectionSocketFactory =   SSLConnectionSocketFactory.getSystemSocketFactory();

	        //SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
	        
	        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(RegistryBuilder
	    	        .<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.getSocketFactory())
	    	        .register("https", sslConnectionSocketFactory).build());
	        
	        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);

		   // httpclient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}    

	}*/

	/*public void init(int maxTotal) {
//		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		//httpclient = HttpClients.custom().setConnectionManager(cm).build();

		//SSLContext sslContext = SSLContexts.createSystemDefault();

        
		try {
			//SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			//SSLContext sslContext = SSLContexts.createSystemDefault();
			
			SSLContext sslContext = SSLContext.getInstance("SSLv2");  
	        X509TrustManager tm = new X509TrustManager() {  
	                @Override  
	                public void checkClientTrusted(X509Certificate[] chain,  
	                        String authType) throws CertificateException {  
	                }  
	                @Override  
	                public void checkServerTrusted(X509Certificate[] chain,  
	                        String authType) throws CertificateException {  
	                }  
	                @Override  
	                public X509Certificate[] getAcceptedIssuers() {  
	                    return null;  
	                }  
	        };  
	        sslContext.init(null, new TrustManager[]{tm}, null);  
		    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(RegistryBuilder
	    	        .<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.getSocketFactory())
	    	        .register("https", new SSLConnectionSocketFactory(sslContext)).build());
		    cm.setMaxTotal(maxTotal);
		    httpclient = HttpClients.custom().setConnectionManager(cm).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}    

	}*/
	
/*	public void init(int maxTotal) {
//		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		//httpclient = HttpClients.custom().setConnectionManager(cm).build();

		//SSLContext sslContext = SSLContexts.createSystemDefault();
		try {
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			//SSLContext sslContext = SSLContexts.createSystemDefault();
		    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(RegistryBuilder
	    	        .<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.getSocketFactory())
	    	        .register("https", new SSLConnectionSocketFactory(sslContext)).build());
		    cm.setMaxTotal(maxTotal);
		    httpclient = HttpClients.custom().setConnectionManager(cm).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 

	}*/
	
	/*
	private CloseableHttpClient buildClient(boolean trustSelfSigned) {
	    try {
	      // if required, define custom SSL context allowing self-signed certs
	      SSLContext sslContext = !trustSelfSigned ? SSLContexts.createSystemDefault() : SSLContexts.custom()
	        .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

	      // set timeouts for the HTTP client
	      int globalTimeout = readFromProperty("bdTimeout", 10000);
	      int connectTimeout = readFromProperty("bdConnectTimeout", globalTimeout);
	      int connectionRequestTimeout = readFromProperty("bdConnectionRequestTimeout", globalTimeout);
	      int socketTimeout = readFromProperty("bdSocketTimeout", globalTimeout);
	      RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT).setConnectTimeout(connectTimeout)
	        .setSocketTimeout(socketTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();

	      // configure caching
	      CacheConfig cacheConfig = CacheConfig.copy(CacheConfig.DEFAULT).setSharedCache(false).setMaxCacheEntries(1000)
	        .setMaxObjectSize(2 * 1024 * 1024).build();

	      // configure connection pooling
	      PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(RegistryBuilder
	        .<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.getSocketFactory())
	        .register("https", new SSLConnectionSocketFactory(sslContext)).build());
	      int connectionLimit = readFromProperty("bdMaxConnections", 40);
	      // there's only one server to connect to, so max per route matters
	      connManager.setMaxTotal(connectionLimit);
	      connManager.setDefaultMaxPerRoute(connectionLimit);

	      // create the HTTP client
	      return CachingHttpClientBuilder.create().setCacheConfig(cacheConfig).setDefaultRequestConfig(requestConfig)
	        .setConnectionManager(connManager).build();
	    } catch (GeneralSecurityException e) {
	      throw new InternalConfigurationException("Failed to set up SSL context", e);
	    }
	  }
	  */
}  
