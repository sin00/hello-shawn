package com.ericsson.li.annotation;

import java.util.Date;

public class Student {
	public Student(int id, String name, String pswd) {
		this.id = id;
		this.name = name;
		this.passwd = pswd;
		this.time = new Date();
		};
		public Student(){};

	private int id;  
    private String name;  
    private String passwd;
    private Date time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String pswd) {
		this.passwd = pswd;
	}  
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", passwd=" + passwd + ", time=" + time + "]";
	}
	
}
