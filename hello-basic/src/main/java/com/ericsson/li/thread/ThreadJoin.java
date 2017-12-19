package com.ericsson.li.thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadJoin extends Thread {
	private static int time = 5;
	public ThreadJoin(String name)  
    {  
        this.setName(name);  
    }  
  
    @Override  
    public void run()  
    {  
    	int tmp = time--;
        System.out.println(this.getName() + " staring..." + tmp);  
    	if (tmp == 0) {
    		System.out.println(" tmp is 0...");  
    		//throw new RuntimeException();
    		System.exit(0);
    	}
        int i = 0;
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(this.getName() + "..." + (i++));  
		}
  
       
    }  
    
    public void run2()  
    {  
    	int tmp = time--;
        System.out.println(this.getName() + " staring..." + tmp);  
        try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
        System.out.println(this.getName() + " end...");  
    }  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args)  
    {  
        System.out.println("main thread starting...");  
  
        List<ThreadJoin> list = new ArrayList<ThreadJoin>();  
  
        for (int i = 1; i <= 6; i++)  
        {  
        	ThreadJoin my = new ThreadJoin("Thrad " + i);  
            my.start();  
            list.add(my);  
        }  
  
        try  
        {  
            for (ThreadJoin my : list)  
            {  
                my.join();  
            }  
        }  
        catch (InterruptedException e)  
        {  
            e.printStackTrace();  
        }  
  
        System.out.println("main thread end...");  
  
    }  
}
