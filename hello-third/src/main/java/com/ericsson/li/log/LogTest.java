package com.ericsson.li.log;
import org.apache.log4j.Logger;   
import org.apache.log4j.PropertyConfigurator;  

public class LogTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	     //Get an instance of the myLogger   
	     Logger myLogger = Logger.getLogger(LogTest.class);   
	      
	     //Get an instance of the childLogger   
	     Logger mySonLogger = Logger.getLogger("myLogger.mySonLogger");   
	     //Load the proerties using the PropertyConfigurator   
	     PropertyConfigurator.configure("log4j.properties");   
	  
	     //Log Messages using the Parent Logger   
	     myLogger.debug("Thie is a log message from the " + myLogger.getName());   
	     myLogger.info("Thie is a log message from the " + myLogger.getName());   
	     myLogger.warn("Thie is a log message from the " +  myLogger.getName());   
	     myLogger.error("Thie is a log message from the " + myLogger.getName());   
	     myLogger.fatal("Thie is a log message from the " + myLogger.getName());   
	  
	     mySonLogger.debug("Thie is a log message from the " + mySonLogger.getName());   
	     mySonLogger.info("Thie is a log message from the " + mySonLogger.getName());   
	     mySonLogger.warn("Thie is a log message from the " +  mySonLogger.getName());   
	     mySonLogger.error("Thie is a log message from the " + mySonLogger.getName());   
	     mySonLogger.fatal("Thie is a log message from the " + mySonLogger.getName()); 
	     
	}

}
