package com.ericsson.li.demo1;

import org.springframework.stereotype.Component;
//@Component //for automation inject to list and map testing. if without @Component then not inject
public class CatHi implements Hi {

	@Override
	public String sayHi() {
		// TODO Auto-generated method stub
		return "Cat: hi";
	}

}
