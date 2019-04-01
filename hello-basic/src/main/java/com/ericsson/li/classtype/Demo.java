package com.ericsson.li.classtype;

public class Demo {

	public static void main(String[] args) {
		F f = new F();
		// 第一种表达方式
		Class c1 = F.class;// 这种表达方式同时也告诉了我们任何一个类都有一个隐含的静态成员变量class
		// 第二种表达方式
		Class c2 = f.getClass();// 这种表达方式在已知了该类的对象的情况下通过getClass方法获取
		// 第三种表达方式
		Class c3 = null;
		try {
			c3 = Class.forName("com.ericsson.li.classtype.F");// 类的全称
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(c1 == c2);
		System.out.println(c1 == c3);
	}
}

class F {
}
