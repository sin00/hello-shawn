package com.ericsson.li.amazon.sqs;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.amazon.sqs.javamessaging.SQSConnection;

public class SQSDemo {
    public static void main(String args[]) throws JMSException {
 
		System.out.println("Hello SQS!");
	
		
		AbstractXmlApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext("SQSSpringConfig.xml");
			context.start();

		       Connection connection;
		        try {
		            connection = context.getBean(Connection.class);
		        } catch( NoSuchBeanDefinitionException e ) {
		            System.err.println( "Could not find the JMS connection to use: " + e.getMessage() );
		            System.exit(3);
		            return;
		        }
		        
		        String queueName;
		        try {
		            queueName = context.getBean("QueueName", String.class);
		        } catch( NoSuchBeanDefinitionException e ) {
		            System.err.println( "Could not find the name of the queue to use: " + e.getMessage() );
		            System.exit(3);
		            return;
		        }
		        
		        if( connection instanceof SQSConnection ) {
		            ExampleCommon.ensureQueueExists( (SQSConnection) connection, queueName );
		        }
		        
		        // Create the session
		        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		        MessageConsumer consumer = session.createConsumer( session.createQueue( queueName) );
		        
		        receiveMessages(session, consumer);		

		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (context != null) {
				context.close();
			}
		}
        System.out.println( "Context closed" );
    }
 
    private static void receiveMessages( Session session, MessageConsumer consumer ) {
        try {
            while( true ) {
                System.out.println( "Waiting for messages");
                // Wait 1 minute for a message
                Message message = consumer.receive(TimeUnit.MINUTES.toMillis(1));
                if( message == null ) {
                    System.out.println( "Shutting down after 1 minute of silence" );
                    break;
                }
                ExampleCommon.handleMessage(message);
                message.acknowledge();
                System.out.println( "Acknowledged message" );
            }
        } catch (JMSException e) {
            System.err.println( "Error receiving from SQS: " + e.getMessage() );
            e.printStackTrace();
        }
    }
}

