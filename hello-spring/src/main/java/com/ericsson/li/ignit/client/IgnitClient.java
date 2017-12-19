package com.ericsson.li.ignit.client;


import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IgnitClient {
	public static void main(String[] args) {
		System.out.println("Hello Ignit!");
	
		
		AbstractXmlApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext("applicationContext-ignite-client.xml");
			context.start();
			IgniteTest igniteTest = (IgniteTest)context.getBean("igniteTest");	
			System.out.println("Hello igniteTest!");
			igniteTest.run();

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
