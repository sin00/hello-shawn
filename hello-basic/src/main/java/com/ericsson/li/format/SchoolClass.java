package com.ericsson.li.format;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class SchoolClass {
    private List<Student> studentList = new ArrayList<>();
    private Map<String, Teacher> teacherMap = new HashMap<>();

    public static void main(String[] args) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.addStudent("zhang3", 1, new Date());
        schoolClass.addStudent("li4", 2, new Date());
        schoolClass.addStudent("wangwu5", 3, new Date());

        schoolClass.addTeacher("Mr Zhang", "math");
        schoolClass.addTeacher("Mr Li", "history");
        System.out.println(JSON.toJSONString(schoolClass));
    }

    private void addStudent(String name, Integer age, Date birthday) {
        studentList.add(new Student(name, age, birthday));
    }

    private void addTeacher(String name, String course) {
        teacherMap.put(course, new Teacher(name, course));
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Map<String, Teacher> getTeacherMap() {
        return teacherMap;
    }

    public void setTeacherMap(Map<String, Teacher> teacherMap) {
        this.teacherMap = teacherMap;
    }
}
