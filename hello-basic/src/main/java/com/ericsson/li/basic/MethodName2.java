package com.ericsson.li.basic;

public class MethodName2 {
	public static void main(String[] args) {
		MethodName2 methodName = new MethodName2();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("class name: " + clazz + " Method Name " + method);
		methodName.anotherMethod();
	}

	private void anotherMethod() {
		String clazz = this.getClass().getName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("class name: " + clazz + " Method Name " + method);
	}
}
