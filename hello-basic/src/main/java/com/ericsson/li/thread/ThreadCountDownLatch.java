package com.ericsson.li.thread;

import java.util.concurrent.CountDownLatch;

public class ThreadCountDownLatch extends Thread {
	private CountDownLatch count;  
	  
    public ThreadCountDownLatch(CountDownLatch count, String name)  
    {  
        this.count = count;  
        this.setName(name);  
    }  
  
    @Override  
    public void run()  
    {  
        System.out.println(this.getName() + " staring...");  
  
        System.out.println(this.getName() + " end...");  
        this.count.countDown();  
    }  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args)  
    {  
        System.out.println("main thread starting...");  
  
        CountDownLatch count = new CountDownLatch(5);  
  
        for (int i = 1; i <= 5; i++)  
        {  
        	ThreadCountDownLatch my = new ThreadCountDownLatch(count, "Thread " + i);  
            my.start();  
        }  
  
        try  
        {  
            count.await();  
        }  
        catch (InterruptedException e)  
        {  
            e.printStackTrace();  
        }  
  
        System.out.println("main thread end...");  
  
    }  
  
}
