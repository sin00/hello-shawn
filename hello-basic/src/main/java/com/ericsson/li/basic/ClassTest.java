package com.ericsson.li.basic;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ericsson.li.collection.MapListTest;

class Person {

	private String name;
	private int age;
	private String address;
	private String phoneNumber;
	private String sex;
	private String grade="2";
	private int cclass;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getCclass() {
		return cclass;
	}

	public void setCclass(int cclass) {
		this.cclass = cclass;
	}

	public void speak() {
		System.out.println("调用的没有参数的方法");
	}

	public void speak(String s) {
		System.out.println("调用有一个参数的方法,参数为:" + s);
	}

	public void speak(String s, int i) {
		System.out.println("调用有两个参数的方法,参数为,参数为:" + s + "和" + i);
	}
	
	public void speakTest(String str, int i) throws Exception {
		if (i == 0) // 调用没有参数的方法
			this.getClass().getMethod(str, new Class[] {}).invoke(this, new Object[] {});
		else if (i == 1) // 调用有一个参数的方法，参数为String类型的s
			this.getClass().getMethod(str, new Class[] { String.class }).invoke(this, new Object[] { "OO" });
		else if (i == 2) // /调用有两个参数的方法 参数分别为String类型的qw和int型的1
			this.getClass().getMethod(str, new Class[] { String.class, int.class }).invoke(this,
					new Object[] { "KK", 1 });
	}

}

public class ClassTest {
	// init person object.
	private Person initPerson() {
		Person p = new Person();
		p.setName("name");
		p.setAge(21);
		p.setAddress("this is my addrss");
		p.setPhoneNumber("12312312312");
		p.setSex("f");
		return p;
	}

	public static void getValueByProperty(Person p, String propertyName)
			throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// get property by the argument propertyName.
		PropertyDescriptor pd = new PropertyDescriptor(propertyName, p.getClass());
		Method method = pd.getReadMethod();
		Object o = method.invoke(p);
		System.out.println("propertyName: " + propertyName + "\t    value is:   " + o);
	}
	
	public static void speakTest(Person p) throws Exception {
		System.out.println("==========speakTest==========");
		p.speakTest("speak", 0);  
		p.speakTest("speak", 1); 
		p.speakTest("speak", 2); 
	}
	
	public static void testClass() {
		try {
			Class<?> cc = Class.forName("com.ericsson.lee.ClassTest");
			MapListTest u = (MapListTest)cc.newInstance();
			System.out.println(u.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		System.getProperties().list(System.out);
		ClassTest test = new ClassTest();
		ClassTest test2 = new ClassTest();
		ClassTest test3 = test2;
		System.out.println("person.getSimpleName:"+ new Person().getClass().getSimpleName());
		System.out.println("test1.getCanonicalName:"+ test.getClass().getCanonicalName());
		System.out.println("test1.getSimpleName:"+ test.getClass().getSimpleName());
		System.out.println("test1.getName:"+ test.getClass().getName());
		System.out.println("test1.class:"+ test.getClass());
		System.out.println("test2.class:"+ test2.getClass());
		System.out.println("test3.class:"+ test3.getClass());
		Person p = test.initPerson();
		List<String> list = new ArrayList<String>();
		// Add all get method.
		// There is no ‘()’ of methods name.
		list.add("getName");
		list.add("getAge");
		list.add("getAddress");
		list.add("getPhoneNumber");
		list.add("getSex");
		list.add("getGrade");
		list.add("getCclass");

		for (String str : list) {
			// Get method instance. first param is method name and second param
			// is param type.
			// Because Java exits the same method of different params, only
			// method name and param type can confirm a method.
			Method method = p.getClass().getMethod(str, new Class[0]);
			// First param of invoke method is the object who calls this method.
			// Second param is the param.
			System.out.println(str + "():  Get Value is   " + method.invoke(p, new Object[0]));
		}

		Field[] fields = p.getClass().getDeclaredFields();
		for (Field field : fields) {
			getValueByProperty(p, field.getName());
		}
		
		
		speakTest(p);
	}
}
