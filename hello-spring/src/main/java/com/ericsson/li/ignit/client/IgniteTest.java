package com.ericsson.li.ignit.client;

import javax.annotation.Resource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class IgniteTest {
	@Resource(name="igniteCacheHandler")
	private IgniteCacheHandler igniteCacheHandler;
	public void run() {
		System.out.println("Igniterun.");
		String key = "aaa";
		String value = "bbb";
		igniteCacheHandler.put(key, value);
		
		System.out.println("IgniteTest:" + igniteCacheHandler.get(key));	
	}
}
