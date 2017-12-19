package com.ericsson.li.ibmmq;

import com.ibm.mq.*;
import javax.jms.*;
import com.ibm.mq.jms.*;

public class PTPSender {

	private String host = "192.168.88.128";
	private String qm = "ESB_OUT";
	private String channel = "SVRCONN_GW_OUT";// "SYSTEM.DEF.SVRCONN";
	private int ccsid = 1208;
	private int port = 20099;
	private QueueConnection qConn = null;
	private QueueSession session = null;
	
	private String inQ = "EIS.QUEUE.RESPONSE.IN.CXZCMES";
	private String outQ = "EIS.QUEUE.REQUEST.OUT.CXZCMES";
	
	private String msgID = null;

	public void init() {
		QueueConnectionFactory qcf = new MQQueueConnectionFactory();

		try {
			((MQQueueConnectionFactory) qcf).setHostName(host);
			((MQQueueConnectionFactory) qcf).setQueueManager(qm);
			((MQQueueConnectionFactory) qcf).setCCSID(ccsid);
			((MQQueueConnectionFactory) qcf).setChannel(channel);
			((MQQueueConnectionFactory) qcf).setPort(port);
			((MQQueueConnectionFactory) qcf).setTransportType(1); //如果不是加这句，老是报错缺少mqjbnd.dll，似乎去连了本机的MQ 服务端

			qConn = qcf.createQueueConnection();
			qConn.start();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		System.out.println("MQ连接成功");

	}

	public void sendMessage() {
		try {
			session = qConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue inputQ = session.createQueue(inQ);
			QueueSender qSender = session.createSender(inputQ);

			TextMessage message = session.createTextMessage();
			message.setText("this is input message from Sender");

			message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// 创建队列的另一种方式，learn.mq也可以指定为其它队列管理器
			//Queue outputQ = session.createQueue("queue://learn.mq/outputQ");
			// 设置消息的回复队列，JMSReplyTo为消息头
			Queue outputQ = session.createQueue(outQ);
			message.setJMSReplyTo(outputQ);

			qSender.send(message, DeliveryMode.NON_PERSISTENT, 7, 0);
			msgID = message.getJMSMessageID();

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将消息从inputQ中转移到outputQ并设置消息的CorrectionID
	public void moveMessage() {
		Queue moveQ_get;
		Queue moveQ_put;
		try {
			moveQ_get = session.createQueue(inQ);
			QueueReceiver qReceiver = session.createReceiver(moveQ_get);
			TextMessage message = (TextMessage) qReceiver.receive();

			message.setJMSCorrelationID(message.getJMSMessageID());

			System.out.println(message.getJMSReplyTo().toString());
			moveQ_put = session.createQueue(message.getJMSReplyTo().toString());
			QueueSender qSender = session.createSender(moveQ_put);
			qSender.send(message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void receiveMessage() {
		try {
			Queue outputQ = session.createQueue(outQ);

			// 获取发送消息对应的回复消息
			QueueReceiver qReceiver = session.createReceiver(outputQ, "JMSCorrelationID='" + msgID + "'");
			// 设置接收消息的超时时间
			TextMessage message = (TextMessage) qReceiver.receive(10000);
			System.out.println("receive message is:" + message.getText());

			// 注册的监听器需要在一线程中运行
			// MyMsgListener msgListener = new MyMsgListener();
			// qReceiver.setMessageListener(msgListener);
			// wait();

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 释放会话及连接
	public void destroy() {

		try {
			session.close();
			qConn.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		PTPSender sender = new PTPSender();
		sender.init();
		sender.sendMessage();
		sender.moveMessage();
		sender.receiveMessage();
		sender.destroy();
	}
}

// 消息监听器
class MyMsgListener implements MessageListener {
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				System.out.println("Listener 接收消息：" + ((TextMessage) message).getText());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}

// 异常监听器,需要有消息监听器一同运行，它将会发送MyExceptionListener对象到onMessage中
// 异常监听器注册到连接对象上，也就是qConn.setExceptionListener(myExceptionListener)
class MyExceptionListener implements ExceptionListener {
	// 此处可放入更多逻辑
	public void onException(JMSException e) {
		System.out.println("队列管理器异常");
		e.printStackTrace();
		System.exit(0);
	}

}
