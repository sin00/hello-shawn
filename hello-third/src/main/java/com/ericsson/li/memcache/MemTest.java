package com.ericsson.li.memcache;
 
import java.util.Date; 
 /*	   
import com.danga.MemCached.MemCachedClient;  
import com.danga.MemCached.SockIOPool;  
 
public class MemTest {  
    public static void main(String[] args) {  
        MemCachedClient client=new MemCachedClient();  
        String [] addr ={"192.168.159.128:11211 192.168.159.128:11212 192.168.159.128:11213"};  
        Integer [] weights = {3};  
        SockIOPool pool = SockIOPool.getInstance();  
        pool.setServers(addr);  
        pool.setWeights(weights);  
        pool.setInitConn(5);  
        pool.setMinConn(5);  
        pool.setMaxConn(200);  
        pool.setMaxIdle(1000*30*30);  
        pool.setMaintSleep(30);  
        pool.setNagle(false);  
        pool.setSocketTO(30);  
        pool.setSocketConnectTO(0);  
        pool.initialize();  
          
//      String [] s  =pool.getServers();  
//        client.setCompressEnable(true);  
 //       client.setCompressThreshold(1000*1024);  
       
       // String str =(String)client.get("key5");  
        //System.out.println(str);  
//      ����ݷ��뻺��  
        client.set("test2","test2");  
          
//      ����ݷ��뻺��,������ʧЧʱ��  
        Date date=new Date(2000000);  
        client.set("test1","test1", date);  
          
//      ɾ������  
//      client.delete("test1");  
          
//      ��ȡ�������  
        String str =(String)client.get("test1");  
        System.out.println(str);  
    }  
}

*/

import com.danga.MemCached.*;
public class MemTest {
	public static void main(String[] args) {
		/*��ʼ��SockIOPool������memcached�����ӳ�*/
		//String[] servers = { "192.168.159.128:11211,192.168.159.128:11212,192.168.159.128:11213" };
		String[] servers = { "192.168.159.128:11213" };
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		pool.setFailover(true);
		pool.setInitConn(10);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaintSleep(30);
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setAliveCheck(true);
		pool.initialize();
		/*����MemcachedClientʵ��*/
		MemCachedClient memCachedClient = new MemCachedClient();
		/*for (int i = 0; i < 10; i++) {
			//��������뵽memcached����
			boolean success = memCachedClient.set("" + i, "Hello!");
			//��memcached�����а�keyֵȡ����
			String result = (String) memCachedClient.get("" + i);
			System.out.println(String.format("set( %d ): %s", i, success));
			System.out.println(String.format("get( %d ): %s", i, result));
		}*/
		
		String key = new String("key5");
		String result = memCachedClient.get(key).toString();
		System.out.println(result);
	}
}