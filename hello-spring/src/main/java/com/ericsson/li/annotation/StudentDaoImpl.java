package com.ericsson.li.annotation;

import java.util.HashMap;
import java.util.Map;

public class StudentDaoImpl implements StudentDao {
	private Map<Integer, Student> mapStudent = new HashMap<Integer, Student>();
	@Override
	public void add(Student student) {
		// TODO Auto-generated method stub
		System.out.println("ADD:" + student.toString());
		mapStudent.put(student.getId(), student);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		Student student = mapStudent.get(id);
		if (student != null) {
			System.out.println("DELETE:" + student.toString());
			mapStudent.remove(id);
		} else {
			System.out.println("DELETE: could not find student by id " + id);
		}
	}

	@Override
	public void search(int id) {
		// TODO Auto-generated method stub
		Student student = mapStudent.get(id);
		if (student != null) {
			System.out.println("SELECT:" + student.toString());
		} else {
			System.out.println("SELECT: could not find student by id " + id);
		}
	
	}
	
}
