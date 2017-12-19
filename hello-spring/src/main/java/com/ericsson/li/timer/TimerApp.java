package com.ericsson.li.timer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Hello world!
 *
 */
public class TimerApp {
	public static void sampleTest() {
/*		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-timer.xml");
		// CronTriggerFactoryBean register =
		System.out.println("----begin---"); 
		CronTriggerFactoryBean myTrigger = (CronTriggerFactoryBean)context.getBean("myTrigger");

		while(true) {
			System.out.println("----begin---");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

	}

	public static void main(String[] args) {
		System.out.println("Hello timer!");
		new ClassPathXmlApplicationContext("applicationContext-timer.xml");
		SchedulerFactoryBean a = null;
		//a.getScheduler().addJob(arg0, arg1);
		
	}
}
