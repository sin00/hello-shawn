package com.ericsson.li.thread.pool;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ThreadPool queue = new ThreadPool(10);  
        // 提交工作任务。  
        queue.execute(new Worker(1));  
        queue.execute(new Worker(2));  
        queue.execute(new Worker(3));
	}

}
