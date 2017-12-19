package com.ericsson.li.type;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;



public class ORTest {
	
	static class OR {
		public byte b[];
	}

	public static int test(int a, int b, int c, int d) {
		return a^b^c^d;
	}
	
	public static void test(byte[] b) {
		byte[] c = new byte[3];
		c[0] = 48;
		c[1] = 0x01;
		c[2] = 49;
		b = c;
	}
	
	public static void test(OR or) {
		byte[] c = new byte[3];
		c[0] = 48;
		c[1] = 0x01;
		c[2] = 49;
		or.b = c;
	}
	
	public static void test1(byte[] c) {
		c = new byte[3];
		c[0] = 48;
		c[1] = 0x01;
		c[2] = 49;
	}
	
	public static void test(int n) {
		n = n ^ 1;
		System.out.println("n=" + n);
		int a = 2;
		a ^= 2;
		System.out.println("a=" + a);
		
		a = 2;
		a ^= 0;
		System.out.println("a=" + a);
	}
	
	public static void print(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
	}
	
	private static int index = 0;
	private static int[] ai = new int[]{0x22, 0x23, 0x23, 0x7e, 0x23, 0x7e, 0x23, 0x22, 0x7e, 0x7e, 0x23, 0x23, 0x7b};
	
	private static void readHead2() {
		int count = 0;
		int nextN = 0x7e;
		while (true) {
			if (index == 30) {
				break;
			}
			int n = read();
			if (n == nextN) {
				count++;
				nextN = 0x23; 
				if (count == 3) {
					System.out.println("index:" + index);
					break;
				}				
			} else {
				System.out.println("expected[" + nextN + "], but read[" + n + "]. index:" + index);
				if (n == 0x7e) {
					nextN = 0x23;
					count = 1;
				} else {
					nextN = 0x7e;
					count = 0;
				}
				
			}
		}
	}
	
	private static int read() {
		int n = 0;
		if (index < ai.length) {
			n = ai[index];
		}
		index++;
		return n;
	}
	
	public static String byte2HexString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i]&(0xFF));
            if (s.length() < 2) {       
                sb.append("0x0").append(s).append(" ");       
            }  else {
            	sb.append("0x").append(s).append(" ");
            }
		}
    	return sb.toString();
    }
	
	private static int index2 = 0;
	//private static int[] ai2 = new int[]{0x22, 0x23, 0x23, 0x7d, 0x01, 0x23, 0x7d, 0x02, 0x23, 0x22, 0x7d, 0x01, 0x7d, 0x01, 0x23, 0x23, 0x7e, 0x01};
	//private static int[] ai2 = new int[]{0x22, 0x23, 0x23, 0x7d, 0x01, 0x23, 0x7d, 0x03, 0x02, 0x23, 0x22, 0x7d, 0x01, 0x7d, 0x01, 0x23, 0x23, 0x7e, 0x01};
	private static int[] ai2 = new int[]{0x7a, 0x05, 0x24, 0x7d, 0x02,0x7e};
	private static int read2() {
		int n = 0;
		if (index2 < ai2.length) {
			n = ai2[index2];
		}
		index2++;
		return n;
	}
	
	private static void readUByte() {		
		ByteArrayOutputStream ubOrg = new ByteArrayOutputStream();
		ByteArrayOutputStream ubEsc = new ByteArrayOutputStream();
		index2 = 0;
		//#^#=0
		int bcc = 0;	
		while (true) {
			int n = read2();
			ubOrg.write(n);			
			if (n == 0x7e) {
				ubEsc.write(n);
				break;
			} else if (n == 0x7d) {
				bcc ^= n;
				
				int k = read2();
				bcc ^= k;
				ubOrg.write(k);
				
				if (k == 0x01) {
					ubEsc.write(0x7d);
				} else if (k == 0x02) {
					ubEsc.write(0x7e);
				} else {
					System.out.println("unknown escape character[0x7d0x" + k + "].");
					break;
				}
			} else {
				bcc ^= n;
				ubEsc.write(n);
			}
		}
		
		System.out.println("bcc:" + bcc);
		System.out.println("org:" + byte2HexString(ubOrg.toByteArray()));
		System.out.println("esc:" + byte2HexString(ubEsc.toByteArray()));
	}
	
	private static void testbcc() {
		//String str = "7e 23 23 07 06 7d 01 00 02 31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 00 00 4f 7e";
		//String str = "23 23 07 06 7d 01 00 02 31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 00 00";
		String str = "23 23 07 06 7d 00 02 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 00 00";
		//String str = "23 23 07 fe 00 7e 31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 00 00 3d 67 65 65 6c 79 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 34 61 63 37 63 32 65 66 64 35 35 33 34 65 63 39 61 34 62 30 01";
		String[] sv = str.split(" ");
		int bcc = 0;
		for (String s :  sv) {
			//System.out.println("s=" + s);
			Integer i = Integer.parseInt(s, 16);
			bcc ^= i;
			//System.out.println("i=" + i);
		}
		System.out.println("bcc=" + bcc + "bcc=0x" + Integer.toHexString(bcc & 0xFF));
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(test(1,2,3,4));
		System.out.println(test(4,3,2,1));
		System.out.println(test(4,1,2,3));
		System.out.println(test(40,10,20,30));
		System.out.println(test(10,20,30,40));
		System.out.println(test(1,2,5,4));
		System.out.println(test(4,5,2,1));
		System.out.println(test(4,1,2,5));
		byte[] b = null;
		test(b);
		System.out.println(b);
		OR or = new OR();
		test(or);
		print(or.b);
		test(0);
		test(1);
		test(2);
		
		byte[] c = null;
		test1(c);
		System.out.println("c:" + c);
		
		readHead2();
		readUByte();
		
		testbcc();
		
	}

}
