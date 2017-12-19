package com.ericsson.li.ibmmq.mgwtool;

import com.ibm.mq.MQException;

public class MQGet extends MQConnector
{
  public MQGet()
  {
    
  }
  
  public String getMessages(String[] args) throws MQException
  {
   String message=getMessageFromQueue();
   return message;
    
  }
  
  public String getMessages() throws MQException
  {
   String message=getMessageFromQueue();
   return message;
    
  }
  
  public static void main(String[] args)
  {
    MQGet mqget = new MQGet();
    MQConnector.DEBUG=false;
    int nullCount = 0;
    try
    {
      mqget.initMq("mqconnect.properties");
      mqget.openQueue();
      
      while (true) {
    	  String msg = mqget.getMessages();
    	  if (msg!=null) {
    		  nullCount = 0;
    		  System.out.println(msg);
    	  } else {
    		  nullCount++;
    		  Thread.sleep(1000);
    		  System.out.println("sleep 1000");
    	  }
    	  
    	  if (nullCount >= 20) {
    		  break;
    	  }
      }
   /*   
      for(String msg=mqget.getMessages(args);msg!=null;msg=mqget.getMessages(args))
      {
         System.out.println(msg);
      }
      */
      mqget.closeQueue();
      mqget.disconnectMq();      
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.println("Usage: "+mqget.getClass().getName()+" ");
    }
  }

}
