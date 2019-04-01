package com.ericsson.li.format;

public class Teacher {
    private String name;
    private String course;

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", course='" + course + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Teacher(String name, String course) {
        this.name = name;
        this.course = course;
    }
}
