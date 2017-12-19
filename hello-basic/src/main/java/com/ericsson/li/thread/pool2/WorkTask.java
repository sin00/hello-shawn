package com.ericsson.li.thread.pool2;

/**
 * 任务接口 
 * 继承它来定义自己具体的工作任务
 */
public interface WorkTask {
     public void runTask();//执行工作任务
        //public int compareTo(mJob job);
     public void cancelTask();
     public int getProgress();
}
