package com.ericsson.li.basic;

import java.lang.reflect.Field;

class Company {
	private String name;
	private String address;

	public String people;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}

public class AttrTest {
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Company c = new Company();

		c.setName("XX科技公司");

		c.setAddress("地球村");

		Field fields[] = c.getClass().getDeclaredFields();// 获得对象所有属性
		Field field = null;
/*		String[] attr = { "name", "address" };
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			field.setAccessible(true);// 修改访问权限
			for (int j = 0; j < attr.length; j++) {
				if (attr[j].equals(field.getName())) {
					System.out.println(field.getName() + ":" + field.get(c));// 读取属性值
				}
			}
		}*/

		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			field.setAccessible(true);// 修改访问权限

			System.out.println(field.getName() + ":" + field.get(c));// 读取属性值

		}
	}
}
