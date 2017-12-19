package com.ericsson.li.collection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;


class AA {
}

class BB extends AA {
}

public class MapListTest {

	private String a = "a";
	private String b = "b";
	private int c = 1;
	
	public MapListTest() {};

	public MapListTest(String x, String y, int z) {
		a = x;
		b = y;
		c = z;
	}
	
	

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		byte[] tt = new byte[21];
		System.out.println(tt.length);
		//testMapClone();
		//arraycopy();
		//testAABB();
		//testArrayOver();

		//listCopy();
		mapCopy();
		//mapToString();
		//parmIsNull();

	// arraysTest();
		//arraysTest2();
		//toStringTest();


	}
	
	public static void testAABB() {
		AA aa = new BB();
		System.out.println(aa.getClass().getName());
		List<String> listStr = new ArrayList<String>();
		listStr.add("AA");
		listStr.add("BB");
		System.out.println(listStr);
	}
	
	public static void testArrayOver() {
		String line = "A|B|";
		String[] sv = line.split("\\|");
		String a, b, c, d;
		try { 
			
			a = sv[0];
			System.out.println(0 + "=>" + sv[0]);
			a = sv[1];
			System.out.println(1 + "=>" + sv[1]);
			a = sv[2];
			System.out.println(2 + "=>" + sv[2]);
			a = sv[3];
			System.out.println(3 + "=>" + sv[3]);
			for (int i = 0; i < 5; i++) {
				System.out.println(i + "=>" + sv[i]);
			}
		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}
	}
	


	public static void mapToString() {
		Map<String, MapListTest> mapUT = new HashMap<String, MapListTest>();
		mapUT.put("1", new MapListTest("1", "2", 3));
		mapUT.put("2", new MapListTest("4", "5", 6));
		mapUT.put("3", new MapListTest("7", "8", 9));
		mapUT.put("4", new MapListTest("7", "8", 9));
		
		System.out.println("1:" +  mapUT);
		MapListTest ut =  new MapListTest("3", "3", 2);
		mapUT.put("1", ut);
		System.out.println("2:" +  mapUT);
		mapUT.put("1", ut);
		System.out.println("3:" +  mapUT);
		
		
		Map<Integer, MapListTest> mapUT2 = new HashMap<Integer, MapListTest>();
		mapUT2.put(6, new MapListTest("1", "2", 3));
		mapUT2.put(7, new MapListTest("4", "5", 6));
		mapUT2.put(8, new MapListTest("7", "8", 9));
		
		System.out.println("4:" +  mapUT2);
		MapListTest ut2 =  new MapListTest("3", "3", 2);
		mapUT2.put(6, ut2);
		System.out.println("5:" +  mapUT2);
		mapUT2.put(6, ut2);
		System.out.println("6:" +  mapUT2);
	}
	
	public static void mapCopy() {
		Map<String, MapListTest> a = new HashMap<String, MapListTest>();
		Map<String, MapListTest> b = new HashMap<String, MapListTest>();
		a.put("1", new MapListTest("1", "2", 3));
		a.put("2", new MapListTest("4", "5", 6));
		a.put("3", new MapListTest("7", "8", 9));
		b.put("4", new MapListTest("0", "0", 0));
		System.out.println("a1," + a);
		System.out.println("b1," + a);
		b.putAll(a);
		a.clear();
		System.out.println("a2," + a);
		System.out.println("b2," + b);
		MapListTest tmp = b.get("1");
		tmp.setA("AAA");
		System.out.println("b3," + b);
	}
	
	public static void parmIsNull() {
		MapListTest ut = new MapListTest();
		System.out.println(ut.getA());
		if (ut.getA() == null) {
			System.out.println("is null");
		}
	}
	


	public static void listCopy() {
		LinkedList<String> q1 = new LinkedList<String>();
		LinkedList<String> q2 = new LinkedList<String>();
		q1.add("ABC");
		q1.add("123");
		q1.add("abc");
		q2.add("KKK");
		
		System.out.println(q1);
		System.out.println(q2);
		q2.addAll(q1);
		q1.clear();
		System.out.println(q1);
		System.out.println(q2);
		
	}

	public static void toStringTest() {
		MapListTest u = new MapListTest("hello", "world", 5);
		System.out.println(u.toString());
	}

	
	public static void testMapClone() {
		LinkedHashMap<String, String> linkedhashmap = new LinkedHashMap<String, String>();
		linkedhashmap.put("1", "a");
		linkedhashmap.put("2", "b");
		linkedhashmap.put("3", "c");	
		linkedhashmap.put("4", "d");
		linkedhashmap.put("0", "o");
		
		HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
		hashmap.put(1, "a");
		hashmap.put(2, "b");
		hashmap.put(3, "c");	
		hashmap.put(4, "d");
		hashmap.put(0, "o");
		
		Iterator<Entry<String, String>> iter = linkedhashmap.entrySet().iterator(); 
		while (iter.hasNext()) { 
			Entry<String, String> entry = iter.next(); 
			System.out.println("0=>" + entry.getKey() + ":" + entry.getValue());
		}
		
		ListIterator<Map.Entry<String, String>> i = new ArrayList<Map.Entry<String, String>>(linkedhashmap.entrySet())
				.listIterator(linkedhashmap.size());
		while (i.hasPrevious()) {
			Map.Entry<String, String> entry = i.previous();
			System.out.println("1=>" + entry.getKey() + ":" + entry.getValue());
		}
		
		LinkedHashMap<String, String> mapL = (LinkedHashMap<String, String>) linkedhashmap.clone();
		linkedhashmap.clear();
		
		iter = mapL.entrySet().iterator(); 
		while (iter.hasNext()) { 
			Entry<String, String> entry = iter.next(); 
			System.out.println("2=>" + entry.getKey() + ":" + entry.getValue());
		}
		
		i = new ArrayList<Map.Entry<String, String>>(mapL.entrySet())
				.listIterator(mapL.size());
		while (i.hasPrevious()) {
			Map.Entry<String, String> entry = i.previous();
			System.out.println("3=>" + entry.getKey() + ":" + entry.getValue());
		}
		
		
		Iterator<Entry<Integer, String>> ii = hashmap.entrySet().iterator(); 
		while (ii.hasNext()) { 
			Entry<Integer, String> entry = ii.next(); 
			System.out.println("h=>" + entry.getKey() + ":" + entry.getValue());
		}
		
		ii = hashmap.entrySet().iterator(); 
		int index = 10;
		while (ii.hasNext()) { 
			Entry<Integer, String> entry = ii.next(); 
			System.out.println("h=>" + entry.getKey() + ":" + entry.getValue());
			
/*		//here is not right	:java.util.ConcurrentModificationException
			if (index < 20) {
				index++;
				hashmap.put(index, "" + index);
			}*/
		}
		
		
		LinkedList<String> ll = new LinkedList<String>();
		ll.add("a");
		ll.add("b");
		ll.add("c");
		ll.add("d");
		ll.add("e");
		
		LinkedList<String> lll = new LinkedList<String>();
		lll.add("m");
		lll.add("n");
		
		LinkedList<String> llll = (LinkedList<String>) lll.clone();
		
		for (String s : lll) {
			System.out.println("lll-1=>" + s);
		}
		lll = (LinkedList<String>) ll.clone();
		ll.clear();
		for (String s : lll) {
			System.out.println("lll-2=>" + s);
		}
		
		for (String s : llll) {
			System.out.println("llll1=>" + s);
		}
		
		//llll.push("j");
		llll.add("k");
		
		System.out.println("llll-pop=>" + llll.pop());
		for (String s : llll) {
			System.out.println("llll2=>" + s);
		}
		
		
		
		for (String s : ll) {
			System.out.println("ll=>" + s);
		}
	}
	public String toString() {
		return "UtilTest [a=" + a + ", b=" + b + ", c=" + c + "]";
	}
	
	

}
