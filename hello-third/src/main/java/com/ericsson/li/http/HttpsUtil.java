package com.ericsson.li.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsUtil {


    /**
     * 忽视证书HostName
     */
    private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslsession) {
            System.out.println("WARNING: Hostname is not matched for cert.");
            return true;
        }
    };

     /**
     * Ignore Certification
     */
	static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {

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


    public static String getMethod(String urlString) {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
        try {
            URL url = new URL(urlString);
            /*
             * use ignore host name verifier
             */
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();


            // Prepare SSL Context
            TrustManager[] tm = { ignoreCertificationTrustManger };
           // SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            SSLContext sslContext = SSLContext.getInstance("TLS", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());


            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);
            
            InputStream reader = connection.getInputStream();
            byte[] bytes = new byte[512];
            int length = reader.read(bytes);


            do {
                buffer.write(bytes, 0, length);
                length = reader.read(bytes);
            } while (length > 0);


            // result.setResponseData(bytes);
            System.out.println(buffer.toString());
            reader.close();
            
            connection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        String repString= new String (buffer.toByteArray());
        return repString;
    }


    public static void main(String[] args) {
       String urlString = "https://54.187.209.172:9444/swagger/v1/noncma/certificate";
       String output = new String(HttpsUtil.getMethod(urlString));
       System.out.println(output);
    }
}
