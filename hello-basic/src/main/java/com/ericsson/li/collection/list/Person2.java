package com.ericsson.li.collection.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Person2 {
    private String name;
    private Integer order;
 
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
 
    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
 
    /**
     * @return the order
     */
    public Integer getOrder() {
        return order;
    }
 
    /**
     * @param order
     *            the order to set
     */
    public void setOrder(Integer order) {
        this.order = order;
    }
    public static void main(String[] args) {
        List<Person2> listA = new ArrayList<Person2>();
        Person2 p1 = new Person2();
        Person2 p2 = new Person2();
        Person2 p3 = new Person2();
 
        p1.setName("name1");
        p1.setOrder(1);
        p2.setName("name2");
        p2.setOrder(2);
        p3.setName("name3");
        p3.setOrder(3);
 
        listA.add(p2);
        listA.add(p1);
        listA.add(p3);
         
        Collections.sort(listA, new Comparator<Person2>() {
            public int compare(Person2 arg0, Person2 arg1) {
                return arg0.getOrder().compareTo(arg1.getOrder());
            }
        });
         
        for (Person2 p : listA) {
            System.out.println(p.getName());
        }
    }
}