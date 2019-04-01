package com.ericsson.li.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SingerAgent implements InvocationHandler {
	private Singer singer;
	
	public SingerAgent(Singer singer) {
		this.singer = singer;
	}
	
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("我是" + singer.getName() + "的经纪人，要找他唱歌/跳舞得先给十万块钱！！");
		return method.invoke(singer, args);
	}

}
