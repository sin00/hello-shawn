package com.ericsson.li.ibmmq;

import java.util.Scanner;  

import javax.jms.JMSException;  
import javax.jms.Message;  
import javax.jms.MessageListener;  
import javax.jms.Queue;  
import javax.jms.QueueConnection;  
import javax.jms.QueueReceiver;  
import javax.jms.QueueSender;  
import javax.jms.QueueSession;  
import javax.jms.Session;  
import javax.jms.TextMessage;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueueConnectionFactory;  
  
  
public class MQSender implements MessageListener{  
    MQQueueConnectionFactory mcf;  
    QueueConnection qconn;  
      
    final String HOSTNAME = "192.168.88.128";  
    final int PORT = 20099;  
    final int CCSID = 1208;
    final String CHANNEL = "SVRCONN_GW_OUT";
    final String QUEUEMANAGER_NAME = "ESB_OUT";  
    final String QUEUE_NAME = "EIS.QUEUE.RESPONSE.IN.CXZCMES";  
    final String QUEUE_NAME2 = "EIS.QUEUE.REQUEST.OUT.CXZCMES";  
    final String USER = "mqm";
    final String PASSWD = "mqm";
    boolean replyed = false;  
      
    private void openConnection() throws JMSException {  
        mcf = new MQQueueConnectionFactory();  
        mcf.setHostName(HOSTNAME);  
        //mcf.setHostName("shawn");
        mcf.setPort(PORT);  
        mcf.setQueueManager(QUEUEMANAGER_NAME);  
        mcf.setCCSID(CCSID);
        mcf.setChannel(CHANNEL);
       // mcf.setTargetClientMatching(arg0);
        mcf.setTransportType(1);

        //qconn = mcf.createQueueConnection(); 
        qconn = mcf.createQueueConnection(USER, PASSWD);
        qconn.start();        
    }  
      
      
    private void sendMessage(String msgInfo) throws JMSException, InterruptedException {  
        openConnection();  
        QueueSession session = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);  
        Queue queue = session.createQueue(QUEUE_NAME);  
        Queue responseQueue = session.createQueue(QUEUE_NAME2);  
        QueueSender sender = session.createSender(queue);  
          
        TextMessage msg = session.createTextMessage();   
//        msg.setJMSCorrelationID("123-123456");  
//        msg.setIntProperty("AccountID", 123);  
        msg.setJMSReplyTo(responseQueue);   //设置回复队列  
        msg.setText(msgInfo);    
        sender.send(msg);   
        System.out.println("消息发送 : JMSMessage" + msg.getJMSMessageID());  
          
        //接收回复信息  
        System.out.println("等待客户端回复队列："+ msg.getJMSReplyTo());  
        String filter = "JMSCorrelationID='" + msg.getJMSMessageID() + "'";    
        QueueReceiver reply = session.createReceiver(responseQueue,filter);  
         
          
        //同步方式等待接收回复  
//        TextMessage resMsg = (TextMessage) reply.receive(60 * 1000);      
//        if(resMsg != null){   
//          System.out.println("客户端回复消息 : " + resMsg.getText() + " JMSCorrelation" + resMsg.getJMSCorrelationID());   
//        }else{  
//          System.out.println("等待超时！");  
//        }  
                  
          
        //异步方式接收回复  
       reply.setMessageListener(this);  
       while(!replyed)  
           Thread.sleep(1000);  
          
        qconn.stop();  
        sender.close();  
        session.close();  
        disConnection();  
    }  
      
    public void onMessage(Message message) {  
        try {  
            String textMessage = ((TextMessage) message).getText();  
            System.out.println("客户端回复消息 : " + textMessage+ " JMSCorrelation" + message.getJMSCorrelationID());            
        } catch (JMSException e) {  
            e.printStackTrace();  
        }finally{  
            replyed = true;   
        }  
    }  
      
    private void disConnection() throws JMSException {        
        qconn.close();   
    }  
      
      
    public static void main(String[] args) throws JMSException, InterruptedException {  
    	//System.load("D:\\Documents\\mqjbnd.dll");
    	//System.out.println(System.getProperty("java.library.path"));

        MQSender ms = new MQSender();  
        Scanner scan = new Scanner(System.in);  
        System.out.print("输入信息：");  
        ms.sendMessage(scan.next());  
        System.out.print("消息发送完毕！");  
    }  
      
}  
