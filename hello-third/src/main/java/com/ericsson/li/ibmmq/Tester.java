package com.ericsson.li.ibmmq;

//JMS classes
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.QueueSender;
import javax.jms.QueueReceiver;
import javax.jms.TextMessage;
import javax.jms.Message;

//JNDI classes
import javax.naming.NamingException;

//Standard Java classes

/**
 * 
 * A class to test JMS with IBM MQ
 *
 */
public class Tester {
	public static String icf = "com.sun.jndi.fscontext.RefFSContextFactory";
	public static String url = "file:/C:/JNDI-Directory";

	public static void main(String[] vars) throws JMSException, NamingException {

		QueueSession session = null;
		QueueConnection connection = null;
		QueueConnectionFactory factory = null;

		QueueSender queueSender = null;
		QueueReceiver queueReceiver = null;

		Queue oQueue = null; // A queue to send messages to
		Queue iQueue = null; // A queue to receive messages from

		try {
			JNDIUtil jndiUtil = new JNDIUtil(icf, url);

			factory = jndiUtil.getQueueConnectionFactory("TestQM_QCF");
			connection = factory.createQueueConnection();

			// Starts (or restarts) a connection's delivery of incoming
			// messages. Messages will not be
			// received without this call.
			connection.start();

			// Indicate a non-transactional session
			boolean transacted = false;
			session = connection.createQueueSession(transacted, Session.AUTO_ACKNOWLEDGE);

			oQueue = jndiUtil.getQueue("OutputTestQueue");
			queueSender = session.createSender(oQueue);

			TextMessage oMsg = session.createTextMessage();
			oMsg.setText("www.devx.com");

			// You can set other message properties as well

			queueSender.send(oMsg);

			iQueue = jndiUtil.getQueue("InputTestQueue");
			queueReceiver = session.createReceiver(iQueue);

			Message iMsg = queueReceiver.receive(1000);

			if (iMsg != null)
				System.out.println(((TextMessage) iMsg).getText());
			else
				System.out.println("No messages in queue ");
		} finally {
			// Always release resources

			if (queueReceiver != null)
				queueReceiver.close();

			if (queueSender != null)
				queueSender.close();

			if (session != null)
				session.close();

			if (connection != null) {
				connection.close();
			}

		}

	}
}
