package com.ericsson.li.druid;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DBPoolTestMain {
    public static void main( String[] args ) {
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-druid.xml");
    	TableOperator druidTableOperator = (TableOperator)context.getBean("druidTableOperator");
    	TableOperator dbcpTableOperator = (TableOperator)context.getBean("dbcpTableOperator");
    	try {
			//druidTableOperator.dropTable();
			//dbcpTableOperator.createTable();
    		druidTableOperator.insert();
    		//dbcpTableOperator.insert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	context.close();
    	System.out.println("done!");
    }
}
