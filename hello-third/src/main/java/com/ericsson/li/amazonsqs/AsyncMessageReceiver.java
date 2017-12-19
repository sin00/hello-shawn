package com.ericsson.li.amazonsqs;

import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;

public class AsyncMessageReceiver {
    public static void main(String args[]) throws JMSException, InterruptedException {
         ExampleConfiguration config = ExampleConfiguration.parseConfig("AsyncMessageReceiver", args);
         
         ExampleCommon.setupLogging();   
         
      // Create the connection factory based on the config
         SQSConnectionFactory connectionFactory = 
                 SQSConnectionFactory.builder()
                     .withRegion(config.getRegion())
                     .withAWSCredentialsProvider(config.getCredentialsProvider())
                     .build();
         
         // Create the connection
         SQSConnection connection = connectionFactory.createConnection();
              
         // Create the session
         Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
         MessageConsumer consumer = session.createConsumer( session.createQueue( config.getQueueName() ) );
         
         ReceiverCallback callback = new ReceiverCallback();
         consumer.setMessageListener( callback );

         // No messages will be processed until this is called
         connection.start();
         
         callback.waitForOneMinuteOfSilence();
         System.out.println( "Returning after one minute of silence" );

         // Close the connection. This will close the session automatically
         connection.close();
         System.out.println( "Connection closed" );
    }
    
    
    private static class ReceiverCallback implements MessageListener {
         // Used to listen for message silence
         private volatile long timeOfLastMessage = System.nanoTime();
         
         public void waitForOneMinuteOfSilence() throws InterruptedException {
              for(;;) {
                   long timeSinceLastMessage = System.nanoTime() - timeOfLastMessage;
                   long remainingTillOneMinuteOfSilence = 
                             TimeUnit.MINUTES.toNanos(1) - timeSinceLastMessage;
                   if( remainingTillOneMinuteOfSilence < 0 ) {
                        break;
                   }
                   TimeUnit.NANOSECONDS.sleep(remainingTillOneMinuteOfSilence);
              }
         }
         

         @Override
         public void onMessage(Message message) {
              try {
                   ExampleCommon.handleMessage(message);
                   message.acknowledge();
                   System.out.println( "Acknowledged message " + message.getJMSMessageID() );
                   timeOfLastMessage = System.nanoTime();
              } catch (JMSException e) {
                   System.err.println( "Error processing message: " + e.getMessage() );
                   e.printStackTrace();
              }
         }
    }
}
