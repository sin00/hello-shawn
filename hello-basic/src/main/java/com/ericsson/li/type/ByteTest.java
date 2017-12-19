package com.ericsson.li.type;

public class ByteTest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//printchar();
		//bytecompair();
		//byteclone();
		//byteint();
		testRtn();
	}

	public static void byteint() {
		// TODO Auto-generated method stub
		byte[] c = new byte[5];
		for (int i = 0; i < c.length; i++) {
			c[i] = -6;
		}

		for (int i = 0; i < c.length; i++) {
			System.out.println("c[" + i + "]=" + c[i]);
		}

		for (int i = 0; i < c.length; i++) {
			int a = c[i];
			// char b = (char)c[i];
			System.out.println("a=" + a);
			// System.out.println("b=" + b);

		}
	}

	public static void byteclone() {
		// TODO Auto-generated method stub
		byte[] c = new byte[5];
		for (int i = 0; i < c.length -1; i++) {
			c[i] = -6;
		}
		
		byte[] b = c.clone();

		for (int i = 0; i < b.length; i++) {
			c[i] = -5;
			System.out.println("b[" + i + "]=" + b[i]);
		}
	}
	
	private static byte[] testRtn(byte[] b) {
		byte[] c = new byte[b.length];
		for (int i = 0; i < b.length; i++) {
			System.out.println("b[" + i + "]=" + b[i]);
			c[i] = b[i];
			b[i] *= 10;
		}
		return c;
	}
	
	private static void testRtn() {
		byte[] b = new byte[5];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte)i;
			System.out.println("b[" + i + "]=" + b[i]);
		}
		
		byte[] c = testRtn(b);
		for (int i = 0; i < c.length; i++) {
			System.out.println("c[" + i + "]=" + c[i]);
		}
		for (int i = 0; i < b.length; i++) {
			System.out.println("b[" + i + "]=" + b[i]);
		}
	}
	
	public static void bytecompair() {
		byte[] b = new byte[2];
		b[0] = 0x01;
		b[1] = (byte)0xFE;
		int a = b[1] & 0xFF;
		System.out.println("a:" + a);
		if (a == 0xFE) {
			System.out.println("a is equal 0xFE");
		} 
		if (b[0] == 0x01) {
			System.out.println("equal:" + b[0]);
		} else {
			System.out.println("unequal:" + b[0]);
		}
		
		if (b[1] == 0xFE) {
			System.out.println("equal:" + b[1]);
		} else {
			System.out.println("unequal:" + b[1]);
		}
		
		if (b[1] == (byte)0xFE) {
			System.out.println("--equal:" + b[1]);
		} else {
			System.out.println("--unequal:" + b[1]);
		}
	}
	
	private static void printchar() {
		String s = "geely";
		byte[] b = s.getBytes();
		byte[] bb = new byte[10];
		System.arraycopy(b, 0, bb, 0, b.length);
		for (int i = 0; i < b.length; i++) {
			System.out.println("b:" + b[i]);
		}
		for (int i = 0; i < bb.length; i++) {
			System.out.println("bb:" + bb[i]);
		}
		char a = 65530;
		//char b = 65536; here error
		int d = 65536;
		char c = (char) d;// here c is 0;
		System.out.println("c:" + (int)c); 
		while (a++ != 0) {
			System.out.println("a:" + (int)a);
		}
	}

}
