package com.ericsson.li.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
	public static void main(String[] args) {
		list2map();
		map2list();
		urlParams();
		string2int();
	}
	
	public static void list2map() {
		List<User> users = Arrays.asList(new User(1, "Tomcat"), new User(2, "Apache"), new User(3, "Nginx"));  
		Map<Integer, User> map = users.stream().collect(Collectors.toMap(obj -> obj.getId() , obj -> obj));  

		System.out.println(map); 
		
		map = users.stream().collect(Collectors.toMap(User::getId , obj -> obj));
		System.out.println(map);
	}
	
	public static void map2list() {
		List<Map<String, String>> list = new ArrayList<>();  
        
		Map<String, String> map1 = new HashMap<>();  
		map1.put("id", "101");  
		map1.put("name", "Tomcat");  
		  
		Map<String, String> map2 = new HashMap<>();  
		map2.put("id", "102");  
		map2.put("name", "Apache");  
		  
		Map<String, String> map3 = new HashMap<>();  
		map3.put("id", "103");  
		map3.put("name", "Nginx");  
		  
		list.add(map1);  
		list.add(map2);  
		list.add(map3);
		
		List<String> ids = list.stream().map(entity -> entity.get("id")).collect(Collectors.toList());  
		System.out.println(ids); 
		
		List<Object> names = Arrays.asList(list.stream().map(entity -> entity.get("name")).toArray());  
		System.out.println(names); 
	}
	
	public static void urlParams() {
		String queryString = "type=1&from=APP&source=homePage";    
		Map<String, String> map = Stream.of(queryString.split("&")).map(obj -> obj.split("=")).collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));    
		System.out.println(map); 
	}
	
	public static void string2int() {
		List<String> strs = Arrays.asList("1","2","3");  
		List<Integer> ints = strs.stream().map(obj -> Integer.valueOf(obj)).collect(Collectors.toList());   
		System.out.println(ints); 
	}
}
	
class User {  
    private int id;  
    private String name;  
    public User(int id, String name) {  
        this.id = id;  
        this.name = name;  
    }  
    public int getId() {  
        return id;  
    }  
    public String toString() {  
        return "User [id=" + id + ", name=" + name + "]";  
    }  
} 