package com.ericsson.li.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Java8InterfaceImpl implements Java8Interface {
	public String getName1(String name) {
        return "Impl " + name;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Java8Interface java8Interface = new Java8InterfaceImpl();
		
		System.out.println(java8Interface.getName("hello") );//

	}

}
