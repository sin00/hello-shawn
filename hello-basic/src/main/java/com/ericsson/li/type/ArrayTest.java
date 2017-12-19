package com.ericsson.li.type;

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


public class ArrayTest {

	private String a = "a";
	private String b = "b";
	private int c = 1;
	
	public ArrayTest() {};

	public ArrayTest(String x, String y, int z) {
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
		int n = 16008;
		float value = ((float)n - 10000) / 10;
		System.out.println("value:" + value);
		byte[] b2 = new byte[]{10, 22};
		value = (float)(((b2[0] & 0xFF) << 8) | (b2[1] & 0xFF)) / 10;
		System.out.println("value2:" + value);
		
		System.out.println("value3:" + (((b2[0] & 0xFF) << 8) | (b2[1] & 0xFF)));
		b2 = new byte[]{0x3A, (byte) 0x97};
		n = (((b2[0] & 0xFF) << 8) | (b2[1] & 0xFF));
		value = (float)n / 1000;
		System.out.println(n + "->value4:" + value);
		
		byte[] tt = new byte[21];
		System.out.println(tt.length);
		String aa = "       ";
		System.out.println("-----1" + aa + "1-------");
		System.out.println("-----1" + aa.trim() + "1-------");
		System.out.println("-----1" + aa.trim().length() + "1-------");
		

		long l = 4294967294L;
		System.out.println("l: " + l);
		System.out.println("l: " + 0xFFFFFFFE);
		if (l == 0xFFFFFFFEl) {
			System.out.println("l == 0xFFFFFFFE");
		} else {
			System.out.println("l != 0xFFFFFFFE");
		}
		//testMapClone();
		//arraycopy();
		//testAABB();
		//testArrayOver();

		//listCopy();
		//mapCopy();
		//mapToString();
		//parmIsNull();

	// arraysTest();
		//arraysTest2();
		//toStringTest();


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
	
	public static void parmIsNull() {
		ArrayTest ut = new ArrayTest();
		System.out.println(ut.getA());
		if (ut.getA() == null) {
			System.out.println("is null");
		}
	}
	


	public static void arraysTest() {

		System.out.println("1.�������� ����� [-23, 1, 3, 4]");
		int[] intArray = new int[] { 4, 1, 3, -23 };
		Arrays.sort(intArray);
		// System.out.println(intArray.toString());
		// System.out.println(intArray);

		for (int i : intArray) {
			System.out.println(i);
		}

		System.out.println("2.�ַ������ȴ�д��Сд ����� [C, a, z]");
		String[] strArray = new String[] { "z", "a", "C" };
		Arrays.sort(strArray);
		// System.out.println(strArray.toString());
		System.out.println("KKKKKK:" + strArray);
		for (String s : strArray) {
			System.out.println(s);
		}

		System.out.println("3.�ϸ���ĸ��˳������Ҳ���Ǻ��Դ�Сд���� Case-insensitive sort ����� [a, C, z]");
		Arrays.sort(strArray, String.CASE_INSENSITIVE_ORDER);
		for (String s : strArray) {
			System.out.println(s);
		}

		System.out.println("4. �������� Reverse-order sort �����[z, a, C]");
		Arrays.sort(strArray, Collections.reverseOrder());
		for (String s : strArray) {
			System.out.println(s);
		}

		System.out.println("5. ���Դ�Сд�������� Case-insensitive reverse-order sort ����� [z, C, a]");
		Arrays.sort(strArray, String.CASE_INSENSITIVE_ORDER);
		Collections.reverse(Arrays.asList(strArray));
		for (String s : strArray) {
			System.out.println(s);
		}
	}

	public static void arraysTest2() {
		String[] strArray = new String[] { "vc_city", "2", "1", "3" };
		Arrays.sort(strArray);

		for (String s : Arrays.copyOf(strArray, strArray.length - 1)) {
			System.out.println(s);
		}

		int i = 2;
		System.out.println("========dimension" + i);
		int j = 4;
		System.out.println(Arrays.binarySearch(strArray, String.valueOf(i)));
		System.out.println(Arrays.binarySearch(strArray, String.valueOf(j)));

		String[] strv = new String[5];
		for (String s : strv) {
			System.out.println(s);
		}
	}
	
	public static void arraycopy() {
		byte[] a = new byte[]{1,2,3};
		byte[] c = null;
		byte[] b = Arrays.copyOf(c, 0);
		System.out.println("b=>" + b);
	}
	public String toString() {
		return "UtilTest [a=" + a + ", b=" + b + ", c=" + c + "]";
	}
	
	

}
