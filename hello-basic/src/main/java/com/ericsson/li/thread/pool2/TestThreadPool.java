package com.ericsson.li.thread.pool2;

/*
 * 一般一个简单线程池至少包含下列组成部分
线程池管理器（ThreadPoolManager）:用于创建并管理线程池
工作线程（WorkThread）: 线程池中线程
任务接口（Task）:每个任务必须实现的接口，以供工作线程调度任务的执行。
任务队列:用于存放没有处理的任务。提供一种缓冲机制。
 * */

/** 
 * 线程池测试类,测试功能如下： 
 * 1、测试线程池创建功能 
 * 2、测试处理并发请求功能 
 * 3、测试关闭功能 
 **/ 
public class TestThreadPool { 
    public static void main(String[] args){ 
        //创建线程池,开启处理请求服务 
        final int threadCount=10; 
        PoolManage pool=PoolManage.getInstance(); 
        pool.init(); 
        //接收客户端请求 
        WorkTask task1=new  WorkTaskAImp("执行超时任务1..."); 
        TaskManager.addTask(task1); 
        final int requestCount=15; 
        for(int i=0;i<requestCount;i++){ 
            WorkTask task=new WorkTaskImp("执行第"+i+"个增加用户操作....."); 
            TaskManager.addTask(task); 
        } 
        /**/ 
    } 
}