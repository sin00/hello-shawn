package com.ericsson.li.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class LinkedHashMapTest {

	public static void main(String[] args) {
		linkkedHashMapTest();
		
	}

	public static void match() {
		// TODO Auto-generated method stub
		String s = "(1){CHI[\ufffd\ufffd|\ufffd\ufffd] || CAT[J] LOGIC[G|D]}+(2){CAT[A] || OF_AMBI[A]}+(3){CHI[\ufffd\ufffd]||CAT[N]}";
		Stack<Character> sc = new Stack<Character>();
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '(' || c[i] == '[' || c[i] == '{') {
				sc.push(c[i]);
			} else if (c[i] == ')') {
				if (sc.peek() == '(') {
					sc.pop();
				}
			} else if (c[i] == ']') {
				if (sc.peek() == '[') {
					sc.pop();
				}
			} else if (c[i] == '}') {
				if (sc.peek() == '{') {
					sc.pop();
				}
			}
		}
		if (sc.empty()) {
			System.out.println("false");
		} else {
			System.out.println("ok");
		}

	}

	public static void linkkedHashMapTest() {
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>(16, 0.75f, true);
		linkedHashMap.put("111", "111");
		linkedHashMap.put("222", "222");
		linkedHashMap.put("333", "333");
		linkedHashMap.put("444", "444");
		loopLinkedHashMap(linkedHashMap);
		linkedHashMap.get("111");
		loopLinkedHashMap(linkedHashMap);
		linkedHashMap.put("222", "2222");
		loopLinkedHashMap(linkedHashMap);
		
	}

	public static void loopLinkedHashMap(LinkedHashMap<String, String> linkedHashMap) {
		Set<Map.Entry<String, String>> set = linkedHashMap.entrySet();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			System.out.print(iterator.next() + "\t");
		}
		System.out.println();
	}
}
