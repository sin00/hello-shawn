package com.ericsson.li.thread;

public class FoodClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FoodContainer container = new FoodContainer(5);  
        Thread producer1 = new Thread(new FoodProducer(container));  
        // Thread producer2 = new Thread(new Producer(container));  
        // producer2.start();  
        Thread consumer1 = new Thread(new FoodConsumer(container));  
        producer1.start();  
        consumer1.start(); 
	}

}
