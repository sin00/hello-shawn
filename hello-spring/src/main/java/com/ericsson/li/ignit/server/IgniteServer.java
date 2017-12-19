package com.ericsson.li.ignit.server;


import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IgniteServer {
	public static void main(String[] args) {	
		AbstractXmlApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext("applicationContext-ignite-server.xml");
			context.start();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (context != null) {
				context.close();
			}
		}
	}
}
