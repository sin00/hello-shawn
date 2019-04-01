package com.ericsson.li.groovy;


import org.springframework.context.support.GenericGroovyApplicationContext;

public class CarApp {
 
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		GenericGroovyApplicationContext context = new GenericGroovyApplicationContext("classpath:groovy/car-config.groovy");
		Car car = (Car) context.getBean("car1");
		car.run();
		car = (Car) context.getBean("car2");
		car.run();
	}
}
