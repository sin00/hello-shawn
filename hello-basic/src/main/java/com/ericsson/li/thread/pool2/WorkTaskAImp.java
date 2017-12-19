package com.ericsson.li.thread.pool2;

/**
* 任务类1
* 正常执行的工作任务 
*/
public class WorkTaskAImp implements WorkTask {

   protected String param;
   public WorkTaskAImp(){
   }
   public WorkTaskAImp(String param){
       this.param=param;
   }
   @Override
   public void runTask() {
       // TODO Auto-generated method stub
      // Log.v("=============>Task1", this.param);
       System.out.println("=============>Task1"+this.param);
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