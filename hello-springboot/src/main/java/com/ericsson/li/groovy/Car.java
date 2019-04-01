package com.ericsson.li.groovy;

public class Car {
	private String name = null;
	private Gasoline gasoline = null;
 
	public Car(Gasoline gasoline) {
		this.gasoline = gasoline;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public void run() {
		int capacity = gasoline.getCapacity();
 
		if (gasoline.getCapacity() > 100) {
			System.out.println("Gasoline capacity is " + capacity + ", " + this.name + " could start to run.");
		} else {
			System.out.println("Gasoline capacity is " + capacity + ", " + this.name + " is not enough to run.");
		}
	}
}

