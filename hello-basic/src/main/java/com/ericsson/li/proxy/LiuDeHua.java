package com.ericsson.li.proxy;

public class LiuDeHua implements Person {

	@Override
	public String sing(String name) {
		// TODO Auto-generated method stub
		System.out.println("刘德华唱"+name+"歌！！");
		return "歌唱完了，谢谢大家！";
	}

	@Override
	public String dance(String name) {
		// TODO Auto-generated method stub
		System.out.println("刘德华跳"+name+"舞！！");
		return "舞跳完了，多谢各位观众！";
	}

}
