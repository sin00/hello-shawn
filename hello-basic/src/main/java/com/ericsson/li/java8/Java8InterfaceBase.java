package com.ericsson.li.java8;

public interface Java8InterfaceBase {
	public default  String getName(String name) {
        return "base " + name;
    }
}
