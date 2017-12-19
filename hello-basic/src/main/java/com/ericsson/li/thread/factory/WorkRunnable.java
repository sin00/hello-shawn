package com.ericsson.li.thread.factory;

public class WorkRunnable implements Runnable
{
	private int taskid = 0;;
	public WorkRunnable(int taskid) {
		this.taskid = taskid;
	}
	@Override
	public void run() {
		 System.out.println("complete a task:" + taskid);
	}

}
