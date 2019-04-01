package com.ericsson.li.proxy;

public class Singer implements Person {
	private String name;
	public Singer(String name) {
		this.name = name;
	}
	public String sing(String song) {
		System.out.println("我是" + name + ",今天给大家带来一首歌曲["+song+"]希望大家喜欢！！");
		return name + "唱"+song+"歌曲";
	}
	
	public String dance(String dance) {
		System.out.println("我是" + name + ",今天给大家带来一支舞蹈["+dance+"]希望大家喜欢！！");
		return name + "跳"+dance+"舞";
	}
	

	public String getName() {
		return name;
	}
	
}
