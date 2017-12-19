package com.ericsson.li.HelloJdbcTemplate;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class JdbcTemplateApp 
{
	public static void testJdbcTemplate() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao)context.getBean("userDao");
        UserDao userDao2 = (UserDao)context.getBean("userDao2");
        if (userDao == userDao2) {
        	System.out.println("userDao == userDao2");
        } else {
        	System.out.println("userDao != userDao2");
        }
        System.out.println(userDao);
        System.out.println(userDao2);
        System.out.println(userDao.getClass().getSimpleName());
        System.out.println(userDao.getClass().getName());
        
        userDao.drop();
        userDao.create();
        
        
        VehicleCity user1 = new VehicleCity(1, "zhangsan","zs");
        System.out.println(user1);
        VehicleCity user2 = new VehicleCity(2, "lisi","ls");
        System.out.println(user2);
        VehicleCity user3 = new VehicleCity(3, "wangwu","ww");
        System.out.println(user3);
        VehicleCity user4 = new VehicleCity(4, "wangwu","ww");
        System.out.println(user4);
        
        //userDao.save(null);
        userDao.save(user1);
        userDao.save2(user2);
        userDao.save3(user3);
        userDao.save(user4);
        
        
        
        List<VehicleCity> userFind = userDao.findUsers();
        System.out.println(userFind.size());
        for (VehicleCity user : userFind) {
        	System.out.println(user.toString());
        }
	}
	
	public static void testJdbcDaoSupport() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentDao studentDao = (StudentDao)context.getBean("studentDao");
        System.out.println(studentDao.getClass().getSimpleName());
        System.out.println(studentDao.getClass().getName());
        
        studentDao.drop();
        studentDao.create();
        
        
        Student student1 = new Student(1, "zhangsan","zs");
        System.out.println(student1);
        Student student2 = new Student(2, "lisi","ls");
        System.out.println(student2);
        Student student3 = new Student(3, "wangwu","ww");
        System.out.println(student3);
        Student student4 = new Student(4, "wangwu","ww");
        System.out.println(student4);
        
        //userDao.save(null);
        studentDao.save(student1);
        studentDao.save2(student2);
        studentDao.save3(student3);
        studentDao.save(student4);
        
        
        
        List<Student> userFind = studentDao.findStudents();
        System.out.println(userFind.size());
        for (Student student : userFind) {
        	System.out.println(student.toString());
        }
	}
	
	public static void testBatchUpdate() {
		  ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	        StudentDao studentDao = (StudentDao)context.getBean("studentDao");
	        System.out.println(studentDao.getClass().getSimpleName());
	        System.out.println(studentDao.getClass().getName());
	        
	        studentDao.drop();
	        studentDao.create();
	        
	        List<Student> students = new ArrayList();
	        
	        Student student1 = new Student(1, "zhangsan","zs");
	        System.out.println(student1);
	        students.add(student1);
	        Student student2 = new Student(2, "lisi","ls");
	        System.out.println(student2);
	        students.add(student2);
	        Student student3 = new Student(3, "wangwu","ww");
	        System.out.println(student3);
	        students.add(student3);
	        Student student4 = new Student(4, "wangwu","ww");
	        System.out.println(student4);
	        students.add(student4);
	        
	        studentDao.insertStudents(students);
	    
	        
	        List<Student> studentFind = studentDao.findStudents();
	        System.out.println(studentFind.size());
	        for (Student student : studentFind) {
	        	System.out.println(student.toString());
	        }
	}
	
	public static void testSetType() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext2.xml");
	      JavaCollection jc=(JavaCollection)context.getBean("javaCollection");

	      jc.getAddressList();
	      jc.getAddressSet();
	      jc.getAddressMap();
	      jc.getAddressProp();
	}
	
	
	public static void testUpdateResult() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentDao studentDao = (StudentDao)context.getBean("studentDao");
        System.out.println(studentDao.getClass().getSimpleName());
        System.out.println(studentDao.getClass().getName());
               
        
        Student student1 = new Student(6, "luqi","lq");
        System.out.println(student1);
        System.out.println("rtn:" + studentDao.save2(student1));
        Student student2 = new Student(6, "huangqi","hq");
        System.out.println(student2);
        System.out.println("rtn:" + studentDao.save(student2));

	}
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //testJdbcTemplate();
        //testJdbcDaoSupport();
        //testBatchUpdate();
        //testSetType();
        testUpdateResult();
        
    }
}
