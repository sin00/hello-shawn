package com.ericsson.li.proxy.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogInterceptor {
	@Pointcut("execution(* regist*(..))||execution(* login*(..))")
	private void log() {
	}

	@Before("log()")
	public void logInterceptor_before() {
		System.out.println("Before LOG:" + " info has loged to file");
	}

	@After("log() && args(a)")
	public void logInterceptor_after(String a) {
		System.out.println("After LOG:" + a + " info has loged to file");
	}
}
