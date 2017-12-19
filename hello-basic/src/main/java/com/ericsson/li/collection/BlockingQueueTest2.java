package com.ericsson.li.collection;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueTest2 {
	 //private static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(5, true); //最大容量为5的数组堵塞队列  
	    private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(5);  
	      
	    private static CountDownLatch producerLatch; //倒计时计数器  
	    private static CountDownLatch consumerLatch;  
	      
	    public static void main(String[] args) {  
	        BlockingQueueTest2 queueTest = new BlockingQueueTest2();  
	        queueTest.test();  
	    }  
	      
	    private void test(){  
	        producerLatch = new CountDownLatch(10); //state值为10  
	        consumerLatch = new CountDownLatch(10); //state值为10  
	          
	        Thread t1 = new Thread(new ProducerTask());  
	        Thread t2 = new Thread(new ConsumerTask());  
	  
	        //启动线程  
	        t1.start();  
	        t2.start();  
	          
	        try {  
	            System.out.println("producer waiting...");  
	            producerLatch.await(); //进入等待状态，直到state值为0，再继续往下执行  
	            System.out.println("producer end");  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	          
	        try {  
	            System.out.println("consumer waiting...");  
	            consumerLatch.await(); //进入等待状态，直到state值为0，再继续往下执行  
	            System.out.println("consumer end");  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	  
	        //结束线程  
	        t1.interrupt();   
	        t2.interrupt();  
	          
	        System.out.println("end");  
	    }  
	      
	    //生产者  
	    class ProducerTask implements Runnable{  
	        private Random rnd = new Random();  
	          
	        public void run() {  
	            try {  
	                while(true){  
	                    queue.put(rnd.nextInt(100)); //如果queue容量已满，则当前线程会堵塞，直到有空间再继续  
	                      
	                    //offer方法为非堵塞的  
	                    //queue.offer(rnd.nextInt(100), 1, TimeUnit.SECONDS); //等待1秒后还不能加入队列则返回失败，放弃加入  
	                    //queue.offer(rnd.nextInt(100));  
	                      
	                    producerLatch.countDown(); //state值减1  
	                    //TimeUnit.SECONDS.sleep(2); //线程休眠2秒  
	                }  
	            } catch (InterruptedException e) {  
	                //e.printStackTrace();  
	            }  catch (Exception ex){  
	                ex.printStackTrace();  
	            }  
	        }  
	    }  
	      
	    //消费者  
	    class ConsumerTask implements Runnable{  
	        public void run() {  
	            try {  
	                while(true){  
	                    Integer value = queue.take(); //如果queue为空，则当前线程会堵塞，直到有新数据加入  
	                      
	                    //poll方法为非堵塞的  
	                    //Integer value = queue.poll(1, TimeUnit.SECONDS); //等待1秒后还没有数据可取则返回失败，放弃获取  
	                    //Integer value = queue.poll();  
	                      
	                    System.out.println("value = " + value);  
	                      
	                    consumerLatch.countDown(); //state值减1  
	                    TimeUnit.SECONDS.sleep(2); //线程休眠2秒  
	                }  
	            } catch (InterruptedException e) {  
	                //e.printStackTrace();  
	            } catch (Exception ex){  
	                ex.printStackTrace();  
	            }  
	        }  
	    }  
}
