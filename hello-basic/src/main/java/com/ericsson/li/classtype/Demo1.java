package com.ericsson.li.classtype;

public class Demo1 {
	public static void main(String[] args) {
		try {
			Class c = Foo.class;
			Foo foo = (Foo) c.newInstance();// foo就表示F类的实例化对象
			foo.print();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		Class c1 = int.class;//int 的类类型
		Class c2 = String.class;//String类的类类型
		Class c3 = void.class;
		System.out.println(c1.getName());
		System.out.println(c2.getName());
		System.out.println(c2.getSimpleName());
		System.out.println(c3.getName());
	}
}

class Foo {
	void print() {
		System.out.println("hh");
	}
}
