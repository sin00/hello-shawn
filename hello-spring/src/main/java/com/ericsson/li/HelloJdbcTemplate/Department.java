package com.ericsson.li.HelloJdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Department {

	private String name;
	private String [] empName;//数组
	private List<JavaCollection> empList;//list集合
	private Set<JavaCollection> empsets;//set集合
	private Map<String,JavaCollection> empMaps;//map集合
	private Properties pp;//Properties的使用

	
	public Set<JavaCollection> getEmpsets() {
		return empsets;
	}
	public void setEmpsets(Set<JavaCollection> empsets) {
		this.empsets = empsets;
	}
	public String[] getEmpName() {
		return empName;
	}
	public void setEmpName(String[] empName) {
		this.empName = empName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<JavaCollection> getEmpList() {
		return empList;
	}
	public void setEmpList(List<JavaCollection> empList) {
		this.empList = empList;
	}
	public Map<String, JavaCollection> getEmpMaps() {
		return empMaps;
	}
	public void setEmpMaps(Map<String, JavaCollection> empMaps) {
		this.empMaps = empMaps;
	}
	public Properties getPp() {
		return pp;
	}
	public void setPp(Properties pp) {
		this.pp = pp;
	}

}