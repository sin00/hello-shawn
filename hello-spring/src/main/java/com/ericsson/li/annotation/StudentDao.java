package com.ericsson.li.annotation;

public interface StudentDao {
	public void add(Student student);
	public void delete(int id);
	public void search(int id);
}
