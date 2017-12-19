package com.ericsson.li.thread.pool;

public class Worker implements Runnable {
    private int id;  
    public Worker(int id) {  
        this.id = id;  
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {  
            Thread.sleep(100);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        System.out.println("线程：" + Thread.currentThread().getName() + " 执行任务" + id);
	}

}
