package com.ericsson.li.thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadJoin2 extends Thread {
	public ThreadJoin2(String name)  
    {  
        this.setName(name);  
    }  
  
    @Override  
    public void run()  
    {  
    	int cnt = 5;
    	for (int i = 0; i < cnt; i++) {
    		System.out.println(this.getName() + ":" + i);  
    	}  
    }  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args)  
    {  
        System.out.println("main thread starting...");  
        ThreadJoin2 threadJoin2 = new ThreadJoin2("AA");
        threadJoin2.start();
  
        
        try  
        {  
        	Thread.sleep(5000);
        	System.out.println("week up"); 
        	System.out.println("threadJoin2 status:" + threadJoin2.getState()); 
        	
        	threadJoin2.join();  
        	System.out.println("join down");
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
  
        System.out.println("main thread end...");  
  
    }  
}
