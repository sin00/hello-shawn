package com.ericsson.li.thread;

public class ThreadGroupTest {
	public static void main(String args[]) {  
        new Thread() {  
            public void run() {  
                int i=100;  
                while (i-->0) {  
                    try {  
                        sleep(10);  
                        printAllThreadNames();  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }.start();  
        int i = 5;  
        while (i-->0) {  
            try {  
                Thread.sleep(10);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    public static void printAllThreadNames() {  
        ThreadGroup group = Thread.currentThread().getThreadGroup();  
        ThreadGroup topGroup = group;  
        int estimatedSize = topGroup.activeCount() * 2;  
        Thread[] slackList = new Thread[estimatedSize];  
        int actualSize = topGroup.enumerate(slackList);  
        System.out.println("begin.....");  
        for (int i = 0; i < actualSize; i++) {  
            System.out.println("Thead " + i + ":" + slackList[i].getName());  
        }  
        System.out.println("end.....");  
    } 
}
