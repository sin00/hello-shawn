package com.ericsson.li.thread.pool2;

/**
* 任务类1
* 正常执行的工作任务 
*/
public class WorkTaskImp implements WorkTask {

   protected String param;
   public WorkTaskImp(){
   }
   public WorkTaskImp(String param){
       this.param=param;
   }
   @Override
   public void runTask() {
       // TODO Auto-generated method stub
       System.out.println("=============>Task0"+this.param);
   }

   @Override
   public void cancelTask() {
       // TODO Auto-generated method stub
      
   }

   @Override
   public int getProgress() {
       // TODO Auto-generated method stub
       return 0;
   }
   
}