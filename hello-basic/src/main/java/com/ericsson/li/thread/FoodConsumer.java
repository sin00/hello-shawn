package com.ericsson.li.thread;

public class FoodConsumer implements Runnable {
    private FoodContainer container;  
    
    public FoodConsumer(FoodContainer container) {  
        super();  
        this.container = container;  
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
        for (;;) {  
            Food food = container.offer();  
            try {  
                Thread.sleep((long) (Math.random() * 3000));  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            if (food != null) {  
                System.out.println(food.getName() + "被消费！");  
            }  
        } 
	}

}
