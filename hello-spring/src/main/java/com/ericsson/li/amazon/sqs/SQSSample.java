package com.ericsson.li.amazon.sqs;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class SQSSample {
	//private static String queueName = "NewOrder";
	private static String queueName = "VehicleProfile";
	//private static String queueName = "VehicleConfig";
	
	
	//private static String endPoint = "sqs.ap-northeast-2.amazonaws.com";
	//private static String profileName = "default";
	
	private static String endPoint = "sqs.cn-north-1.amazonaws.com.cn";
	private static String profileName = "tfsProduction";
	
    public static void main(String args[]) throws JMSException {

        SQSConnectionFactory connectionFactory =
                SQSConnectionFactory.builder()
                .withEndpoint(endPoint)
                .withAWSCredentialsProvider(new ProfileCredentialsProvider(profileName))
                        .build();
        
        
        // Create the connection
        SQSConnection connection = connectionFactory.createConnection();
        
        // Create the queue if needed
        //ExampleCommon.ensureQueueExists(connection, queueName);
            
        // Create the session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
               
        sendMessages(session, "hello aws sqs");
        receiveMessages(session);
 
        // Close the connection. This will close the session automatically
        connection.close();
        System.out.println( "Connection closed" );
    }
 
    private static void sendMessages( Session session, String msg) {    
        try {
        	MessageProducer producer = session.createProducer( session.createQueue(queueName) );

                TextMessage message = session.createTextMessage(msg);
                producer.send(message);
                System.out.println( "Send message " + message.getJMSMessageID() );
            
        } catch (JMSException e) {
            System.err.println( "Failed sending message: " + e.getMessage() );
            e.printStackTrace();
        }
    }
    
    private static void receiveMessages(Session session) {
        try {
      	  MessageConsumer consumer = session.createConsumer(session.createQueue(queueName));
            while( true ) {
                System.out.println( "Waiting for messages");
                // Wait 1 minute for a message
/*                Message message = consumer.receive(TimeUnit.MINUTES.toMillis(1));
                if( message == null ) {
                    System.out.println( "Shutting down after 1 minute of silence" );
                    break;
                }*/
                Message message = consumer.receive();
                //ExampleCommon.handleMessage(message);
                TextMessage txtMessage = ( TextMessage ) message;
                System.out.println( "receive:\t" + txtMessage.getText() );
                message.acknowledge();
                System.out.println( "Acknowledged message " + message.getJMSMessageID() );
            }
        } catch (JMSException e) {
            System.err.println( "Error receiving from SQS: " + e.getMessage() );
            e.printStackTrace();
        }
    }
}

