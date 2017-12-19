package com.ericsson.li.timer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerJob {
	//private static Logger logger = LoggerFactory.getLogger(TimerJob.class);
    public void run() {  
        try {  
            System.out.println("Run TimerJob at: "+new Date());  
        } catch(Exception e) {  
        	System.out.println("Run TimerJob error at: "+new Date() + ", with error message: " + e.getMessage());  
        }  
    } 
}
