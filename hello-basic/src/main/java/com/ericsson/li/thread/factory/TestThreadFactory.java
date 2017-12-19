package com.ericsson.li.thread.factory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadFactory {

	public static void main(String[] args) {
		//创建线程（并发）池，自动伸缩(自动条件线程池大小)
		//ExecutorService es =  Executors.newCachedThreadPool(new WorkThreadFactory());
		ExecutorService es = Executors.newFixedThreadPool(2, new WorkThreadFactory());
		//ExecutorService executorService = new ThreadPoolExecutor(taskConfig.getCorePoolSize(), taskConfig.getMaximumPoolSize(),
		//taskConfig.getKeepAliveTime(), TimeUnit.MINUTES,
		//new ArrayBlockingQueue<Runnable>(taskConfig.getBlockSize()), new TaskThreadFactory(),
		//new TaskRejectedExecutionHandler());

		int i = 0;
		//同时并发5个工作线程
		es.execute(new WorkRunnable(i++));
		es.execute(new WorkRunnable(i++));
		es.execute(new WorkRunnable(i++));
		es.execute(new WorkRunnable(i++));
		es.execute(new WorkRunnable(i++));
		es.execute(new WorkRunnable(i++));
		es.execute(new WorkRunnable(i++));
		es.execute(new WorkRunnable(i++));
		es.execute(new WorkRunnable(i++));
		es.execute(new WorkRunnable(i++));
		//指示当所有线程执行完毕后关闭线程池和工作线程，如果不调用此方法，jvm不会自动关闭
	       es.shutdown();  
	    
	    try {  
	         //等待线程执行完毕，不能超过2*60秒，配合shutDown
	          es.awaitTermination(2*60, TimeUnit.SECONDS);  
	     } catch (InterruptedException e) {  
	            e.printStackTrace();  
	     }  
	}
	
}
