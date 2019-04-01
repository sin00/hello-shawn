package com.ericsson.li.proxy.aop;

public class RegisterService {
	public boolean regist(String name) {
		if (name == null)
			return false;
		System.out.println(name + " regist");
		return true;
	}

	public boolean login(String name) {
		if (name == null)
			return false;
		System.out.println(name + " login");
		return true;
	}
}
