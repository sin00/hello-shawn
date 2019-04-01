package com.ericsson.li.java8;

public interface Java8Interface extends Java8InterfaceBase {
	public default String getName(String name) {
        return "interface " + name;
    }
}
