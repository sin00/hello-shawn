package com.ericsson.li.ibmmq.mgwtool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;

import com.ibm.mq.MQException;

public class RealtimeTool extends MQConnector {

	public static void main(String[] args) {
		
		MQSend mqsend = new MQSend();
		MQConnector.DEBUG = false;
		try {
			mqsend.initMq("realtime.properties");
			mqsend.openQueue();
		} catch (MQException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		InputStream fi;
		try {
			//fi  = new FileInputStream(args[0]);
			//fi = new FileInputStream("data/realtime-vehicle.xml");
			fi = new FileInputStream("data/realtime-customer.xml");
			byte[] resMsg = new byte[fi.available()];
			fi.read(resMsg);
			fi.close();
			
			System.out.println((new String(resMsg, "utf-8")));
			mqsend.putMessageToQueue(resMsg);
			System.out.println("done!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			mqsend.closeQueue();
			mqsend.disconnectMq();
		} catch (MQException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
	}
}
