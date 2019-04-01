package com.ericsson.li.demo1;

import org.springframework.stereotype.Component;

@Component
public class DogHi implements Hi {

	@Override
	public String sayHi() {
		// TODO Auto-generated method stub
		return "Dog: hi";
	}

}
