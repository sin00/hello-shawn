package com.ericsson.li.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskJob {
	//private static Logger logger = LoggerFactory.getLogger(TimerJob.class);
    public void run() {  
        try {  
            System.out.println("Run TaskJob[" + this +"]at: "+new Date());  
        } catch(Exception e) {  
        	System.out.println("Run TaskJob error at: "+new Date() + ", with error message: " + e.getMessage());  
        }  
        throw new RuntimeException("end---------haha");
    } 
    
    public void run2() {  
        try {  
            System.out.println("Run TaskJob[" + this +"]at--2: "+new Date());  
        } catch(Exception e) {  
        	System.out.println("Run TaskJob error at--2: "+new Date() + ", with error message: " + e.getMessage());  
        }  
    }
}
