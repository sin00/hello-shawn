package com.ericsson.li.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class LambdaTest {
	public static void main(String[] args) {
		testPredicate();
	}
	
	public static void testPredicate() {
		List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

		System.out.println("Languages which starts with J :");
		filter(languages, (str)-> str.startsWith("J"));

		System.out.println("Languages which ends with a ");
		filter(languages, (str) -> str.endsWith("a"));

		System.out.println("Print all languages :");
		filter(languages, (str) -> true);

		System.out.println("Print no language : ");
		filter(languages, (str) -> false);

		System.out.println("Print language whose length greater than 4:");
		filter(languages, (str) -> str.length() > 4);
		
		Predicate<String> startsWithJ = (n) -> n.startsWith("J");
		Predicate<String> fourLetterLong = (n) -> n.length() == 4;
		
		languages.stream().filter(startsWithJ.and(fourLetterLong)).forEach((n)->System.out.println("Name, which starts with 'J' and four letter long is : " + n));
		
		 /*Predicate<String> startsWithJ = (n) -> n.startsWith("J");
		 Predicate<String> fourLetterLong = (n) -> n.length() == 4;
		   
		 names.stream()
		      .filter(startsWithJ.and(fourLetterLong))
		      .forEach((n) -> System.out.print("\nName, which starts with
		            'J' and four letter long is : " + n));
*/
	}
	
	public static void filter(List<String> names, Predicate<String> condition) {
		for (String name : names) {
			if (condition.test(name)) {
				System.out.println(name + " ");
			}
		}
	}

	public static void testSingle() {
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return b.compareTo(a);
			}
		});
		
		Iterator<String> it  = names.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		
		Collections.sort(names, (String a, String b) -> {  
		    return a.compareTo(b);  
		});
		it  = names.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		
		List<String> listString = new ArrayList();
		listString.add("a");
		listString.add("b");
		listString.forEach((String s) -> { System.out.println("hh:" + s); });
		
		List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API",
				 "Date and Time API");
		features.forEach((String s) -> { System.out.println("ff:" + s);});
		features.forEach(s -> { System.out.println("dd:" + s);});
		
		for (int j = 0; j < 5; j++) {
			new Thread(()->{
				for (int i = 0; i < 10; i++)
	            System.out.println(Thread.currentThread().getName() + "-Lambda Expression-" + i);
		    }).start();
		}
	}

}
