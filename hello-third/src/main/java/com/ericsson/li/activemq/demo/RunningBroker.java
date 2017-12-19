package com.ericsson.li.activemq.demo;

import java.net.URI;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.broker.BrokerFactory;  
import org.apache.activemq.broker.BrokerService;  
  
/** 
 * 启动ActiveMQ的代理Broker 
 *  
 * @author  XX 
 * @version  [版本号, Apr 28, 2013 3:21:20 PM ] 
 * @see  [相关类/方法] 
 * @since  [产品/模块版本] 
 */  
public class RunningBroker {  
    public static void main(String[] args){  
        try {  
            codeByRunning() ;  //启动Broker  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 应用程序中以编码的方式启动 
     * 创 建 人:  XX 
     * 创建时间:  May 23, 2013 3:26:22 PM   
     * @throws Exception 
     * @see [类、类#方法、类#成员] 
     */  
    public static void codeByRunning() throws Exception{  
        BrokerService broker =new BrokerService();  
        broker.setBrokerName("testName");//如果启动多个Broker时，必须为Broker设置一个名称  
        broker.addConnector("tcp://localhost:61616");  
        //broker.addConnector(ActiveMQConnection.DEFAULT_BROKER_URL);
        broker.start();  
    }  
      
    /** 
     * 以BrokerFactory的方式启动 
     * 创 建 人: XX 
     * 创建时间:  May 23, 2013 3:26:53 PM   
     * @throws Exception 
     * @see [类、类#方法、类#成员] 
     */  
    public static void factoryByRunning()throws Exception{  
        BrokerService broker =BrokerFactory.createBroker(new URI("broker:tcp://localhost:61616"));  
        broker.start();  
    }  
}  