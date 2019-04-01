package com.ericsson.li.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SingerProxy {
	private Singer singer;
	
	public SingerProxy(Singer singer) {
		this.singer = singer;
	}
	
	public Person getProxy1() {
        return (Person) Proxy.newProxyInstance(LiuDeHuaProxy.class
                .getClassLoader(), singer.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method,
                            Object[] args) throws Throwable {
                		System.out.println("我是" + singer.getName() + "的经纪人，要找他唱歌/跳舞得先给十万块钱！！");
                		return method.invoke(singer, args);
                	}
                });
    }
	
	public Person getProxy() {
		InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {
			System.out.println("我是" + singer.getName() + "的经纪人，要找他先给十万块钱！！");
			return method.invoke(singer, args);
		};
		return (Person) Proxy.newProxyInstance(LiuDeHuaProxy.class.getClassLoader(), singer.getClass().getInterfaces(),
				handler);
	}
	
}
