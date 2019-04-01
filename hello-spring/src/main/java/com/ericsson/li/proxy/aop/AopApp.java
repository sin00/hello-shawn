package com.ericsson.li.proxy.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class AopApp 
{
	public static void sampleTest() {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-aop.xml");
		RegisterService register = (RegisterService)context.getBean("register");
		
		register.regist("aop1");
		register.login("aop1");

	}
	
    public static void main( String[] args ){
        System.out.println( "Hello aop!" );
        sampleTest();
        
    }
}
