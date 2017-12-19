package com.ericsson.li.file;

import java.io.*;
import java.util.Arrays;
import java.util.Date;

public class SerializaTest {
    public static void main(String[] args) throws Exception {
        /*其中的  D:\\objectFile.obj 表示存放序列化对象的文件*/

        String fileName = "D:\\objectFile1.obj";
        //序列化对象
    	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
/*    	Object obj = new Object();
    	out.writeObject(obj);*/
    	
    	byte[] byteData1 = new byte[4];
        byteData1[0] = 0x23;
        byteData1[1] = 0x24;
        byteData1[2] = 0x25;
        byteData1[3] = 0x26;
        
        byte[] byteData2 = new byte[7];
        System.arraycopy(byteData1, 0, byteData2, 0, 4);
        byteData2[3] = 0x30;
        byteData2[4] = 0x31;
        byteData2[5] = 0x32;
        
        Customer customer1 = new Customer("王麻子", 24, byteData1);  
        Customer customer2 = new Customer("张大炮", 34, byteData2); 
        out.writeObject("你好!");    //写入字面值常量
        out.writeObject(byteData1); 
        out.writeObject(new Date());    //写入匿名Date对象
        out.writeObject(customer1);    //写入customer对象
        out.writeObject(customer2);    //写入customer对象
        out.close();

        
        //反序列化对象
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        System.out.println("obj1 " + (String) in.readObject());    //读取字面值常量
        System.out.println("byteData1 " + Arrays.toString((byte[]) in.readObject())); 
        System.out.println("obj2 " + (Date) in.readObject());    //读取匿名Date对象
        Customer obj3 = (Customer) in.readObject();    //读取customer对象
        System.out.println("Customer1 " + obj3);
        Customer obj4 = (Customer) in.readObject();    //读取customer对象
        System.out.println("Customer2 " + obj4);
        in.close();
    }
}

class Customer implements Serializable {
    private String name;
    private int age;
    private byte[] byteData;
    public Customer(String name, int age, byte[] data) {
        this.name = name;
        this.age = age;
        this.byteData = data;

        
    }
	@Override
	public String toString() {
		return "Customer [name=" + name + ", age=" + age + ", byteData=" + Arrays.toString(byteData) + "]";
	}
    
}
