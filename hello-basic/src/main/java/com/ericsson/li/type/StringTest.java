package com.ericsson.li.type;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;

public class StringTest {
	private String aa = "999";
	public static void splitTest() {
		// SSSDDDAA_YYYYMMDDHHMISS.00000
		String fileName = "DMSTSC01_20151221000000.00001";
		System.out.println(fileName.split("_")[1].substring(0, 8));
		System.out.println(fileName.substring(6, 8));

		String strTest = "a||c|d";
		String[] sv = strTest.split("\\|");
		for (String st : sv) {
			System.out.println(st + "==>" + st.length());
		}

		String nn = null;
		System.out.println("nn:" + nn);
		Integer aa = null;
		try {
			aa = Integer.parseInt("");
		} catch (Exception e) {
			System.out.println("dddd aa is null ");
		}
		if (aa == null) {
			System.out.println("aa is null ");
		} else {
			System.out.println("aa is :" + Integer.parseInt(""));
		}
	}
	
	
	public static void strTest2(String a) {
		StringBuilder sb = new StringBuilder();
/*		sb.append(1).append(2).append("opq");
		a = sb.toString();
		a = "kkkkk";
		sb.setLength(0);*/
		sb.append("\r\nVehicle\r\n{").append("\r\n\t");
		sb.append("id: ").append(1).append("\r\n\t");
		sb.append("spead: ").append(100).append("\r\n\t");
		sb.append("hahaha: ").append(1000);
		sb.append("\r\n}");
		int[] ii = {1, 2, 3};
		
		//System.out.println("SB is :" + sb.toString());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(sb.toString());
		System.out.println("ii:" + ii);
	}
	
	public static void strTest3(StringBuilder sb) {
		sb.append(1).append(2).append("opq");
	}

	public static void strTest() {
		
		String str = null;
		strTest2(str);
		System.out.println("abc:" + str);
		
		StringBuilder sb = new StringBuilder();
		strTest3(sb);
		System.out.println("cde:" + sb.toString());

		String oldKey = "20150101^GZ^A";
		System.out.println(oldKey);
		String subKey = oldKey.substring(oldKey.indexOf("^"));
		System.out.println(subKey);
		String newKey = "201501" + subKey;
		System.out.println(newKey);
		int x = 5;
		int y = 10;
		System.out.println(newKey + x + y);
		System.out.println(oldKey.substring(8));
		System.out.println(oldKey.substring(8).equals("^GZ^A"));

		String[] dim = oldKey.split("\\^");
		String oldKey2 = "20150101|GZ|A";
		String[] dim2 = oldKey2.split("\\|");
		System.out.println("dim:" + dim.length);
		System.out.println("dim2:" + dim2.length);
		System.out.println(dim2);

		String strTest = "1, vc_city,2,4";
		System.out.println(strTest);
		String strTest2 = strTest.replaceAll("vc_city", "");
		System.out.println(strTest2);
		// String[] stra =

	}
	
	public static void testNewStringTest() {
		StringTest st = null;
		newStringTest(st);
		System.out.println(st.aa);
	}
	
	public static void newStringTest(StringTest st) {
		st = new StringTest();
	}

	public static void bigdecimalTest() {

		BigDecimal[] sum = new BigDecimal[10];
		String[] str = { "1", "2", "3", "4" };
		for (int i = 0; i < sum.length; i++) {
			if (sum[i] == null) {
				sum[i] = new BigDecimal(0);
			}
			for (int j = 0; j < str.length; j++) {
				sum[i] = sum[i].add(new BigDecimal(str[j]));
			}
		}

		for (int i = 0; i < sum.length; i++) {
			System.out.println("sum" + i + ":" + sum[i]);
		}

	}
	

	public static void streamWriterTest() {
		try {

			byte[] sim = { (byte) 0xbc, (byte) 0xf2, // 简
					(byte) 0xcc, (byte) 0xe5, // 体
					(byte) 0xd6, (byte) 0xd0, // 中
					(byte) 0xce, (byte) 0xc4 }; // 文
			InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(sim), "GB2312");
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out, "GB2312"));
			PrintStream printStream = new PrintStream(System.out, true, "GB2312");
			int in;
			while ((in = inputStreamReader.read()) != -1) {

				printWriter.println((char) in);

				printStream.println((char) in);

			}
			inputStreamReader.close();
			printWriter.close();
			printStream.close();

		} catch (ArrayIndexOutOfBoundsException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	private static void commontest() {
		
		String aaa = "   ddd   ";
		System.out.println(aaa + "cccc");
		System.out.println(aaa.trim() + "cccc");
		System.out.println(aaa + "cccc");
		StringBuilder  sb = new StringBuilder();
		String sbs = sb.toString();
		System.out.println(sbs.length() + "sbs=" + sb.toString());
		
		String msg = "aabbc.cddeffg";
		System.out.println(msg.contains("bc"));
		System.out.println(msg.contains("efg"));
		System.out.println(msg.contains("abcdefg"));
		System.out.println(msg.contains("bbc.cddef"));
		System.out.println(msg.contains("c.c"));
		
		System.out.println("null:" + msg.equals(null));
		
	}
	
	public static void testSubstring() {
		String str = "CN=Global Vehicle Issuing Production CA,"
				+ "OU=Zhejiang Geely Holding Group Co. Ltd, O=Geely Automobile Holdings"
				+ "Ltd, L=Hangzhou, ST=Zhejiang, C = CN, DC=Geely, DC=Connected Car";
		String str1 = "AABBCCDD";
		//String str2 = str1.substring(str1.length()+2); //index out of range:-2
		String str2 = str1.substring(str1.length());
		if (str2 == null) {
			System.out.println("str1.substring(str1.length()) is null");
		} else {
			System.out.println(str2.length() + "-str1.substring(str1.length()):" + str2);
		}
		
/*		System.out.println("ST=" + parseStrValue(str, "ST="));
		System.out.println("C=" + parseStrValue(str, "C="));*/
		
		HashMap<String, String> hashmap = new HashMap<String, String>();
		String[] strArry = str.split(",");
		for (String su : strArry) {
			String[] tmpArry = su.trim().split("=");
			if (tmpArry.length != 2) {
				System.out.println("invalid data:" + su);
				continue;
			}
			
			String key = tmpArry[0].trim();
						
			String value = hashmap.get(tmpArry[0]);
			if (value != null) {
				hashmap.put(key, value + "," + tmpArry[1].trim());
			} else {
				hashmap.put(key, tmpArry[1].trim());
			}
		}
		
		System.out.println("ST=" + hashmap.get("ST"));
		System.out.println("C=" + hashmap.get("C"));
		System.out.println("DC=" + hashmap.get("DC"));
	}
	
	/*
	public static String parseStrValue(String str, String key) {
		int pos = str.indexOf(key);
		if (pos == -1) {
			return null;
		}
		String substr = str.substring(pos);
		pos  = key.length();
		if (pos > substr.length()) {
			return null;
		}
		substr = substr.substring(pos);
		pos = substr.indexOf(",");
		if (pos != -1) {
			return substr.substring(0, pos).trim();
		}
		pos = substr.indexOf("\"");
		if (pos != -1) {
			return substr.substring(0, pos).trim();
		}	
		return null;
	}
	
	public static String parseStrValue1(String str, String key) {
		int pos = str.indexOf(key);
		if (pos == -1) {
			return null;
		}
		String substr = str.substring(pos);
		pos = substr.indexOf("=");
		
		if (pos == -1) {
			return null;
		}
		if (++pos > substr.length()) {
			return null;
		}
		substr = substr.substring(pos);
		pos = substr.indexOf(",");
		if (pos != -1) {
			return substr.substring(0, pos).trim();
		}
		pos = substr.indexOf("\"");
		if (pos != -1) {
			return substr.substring(0, pos).trim();
		}	
		return null;
	} */
	
	public static void testChar() {
		Integer i = 5;
		System.out.println("i=5:" + (i==0x05));
		String a = "2hello";
		System.out.println("charat:" + (a.charAt(0)=='2'));
		System.out.println("charat:" + (a.charAt(0)==2));
		System.out.println("substring:" + (3+a.substring(1)));
	}
	
	public static void testByte(String str) {
		//String str = "ABCDE12345FGH";	
		byte[] b = str.getBytes();
		System.out.println("str.length:" + str.length());
		System.out.println("b.length:" + b.length);
		for (int i = 0; i < b.length; i++) {
			System.out.println("b[" + i + "]=" + b[i]);
		}
		
		String s = new String(b);
		System.out.println(s);
		
	}
	
	public static void testReplace() {
		System.out.println("abac".replace("a", "\\a")); //\ab\ac
		System.out.println("abac".replaceAll("a", "\\a")); //abac
		System.out.println("abac".replaceFirst("a", "\\a")); //abac
		System.out.println("abac".replaceAll("a(\\w)", "$1$1")); //bbcc
		System.out.println("abac".replaceFirst("a(\\w)", "$1$1")); //bbac
		System.out.println("abac".replaceAll("a", "\\\\a")); //\ab\ac
		System.out.println("abac".replaceFirst("a", "\\\\a")); //\abac
	}
	
	public static void testContain() {
		String str = "ABCDE20170503FGH";
		System.out.println(str.contains("2017"));
		System.out.println(str.contains("20170503"));
		System.out.println(str.matches("20170503"));
		System.out.println("20170503".matches(str));
		System.out.println("20170503".matches("20170503"));
		System.out.println(str.matches("[*]20170503[*]"));
		
	}
	
	public static String getUnicode(String source) {
		String returnUniCode = null;
		String uniCodeTemp = null;
		for (int i = 0; i < source.length(); i++) {
			uniCodeTemp = "\\u" + Integer.toHexString((int) source.charAt(i));// 使用char类的charAt()的方法
			returnUniCode = returnUniCode == null ? uniCodeTemp : returnUniCode + uniCodeTemp;
		}
		System.out.println(returnUniCode);
		return returnUniCode;// 返回一个字符的unicode的编码值
	}
	
	public static void testDelete() {
		//String subjectRDN1 = "CN= \u001cGeely Code Signing Service for Web based App Production, OU=Geely Automobile Holdings Ltd, O=Zhejiang Geely Holding Group Co., Ltd, L=Hangzhou, ST=Zhejiang, C=CN, DC=Geely, DC=Connected Car";
		//String issuerRDN1 = "CN=Global Vehicle Issuing Cloud Test CA, OU=Geely Automobile Holdings Ltd, O=Zhejiang Geely Holding Group Co\u001e Ltd, L=Hangzhou, ST=Zhejiang, C=CN, DC=Connected Car, DC=Geely";
		//Co.\, Ltd
		//Holding Group Co\u001e Ltd
		//Holding Group Co.\u001e Ltd
		String aa = "CN= \u001cGeely Code Signing Service for Web based App Production, OU=Geely Automobile Holdings Ltd, O=Zhejiang Geely Holding Group Co\u001e Ltd, O=Zhejiang Geely Holding Group Co., Ltd";
		System.out.println(aa);
		System.out.println(reviseDN(aa));
		//System.out.println(aa.replace("\u001e", ".\\,").replaceAll("\\s+\u001c", "").replace("Co.,", "Co.\\,"));
		String bb = "Group Co\u001e Ltd, C=CN, Group Co.\u001e Ltd, CN= \u001cGeely Co.\\, Ltd, Group Co.,Ltd";
		System.out.println(bb);
		//System.out.println(bb.replaceAll("\\s*(\u001e|\u001c)", ""));
		//System.out.println(bb.replaceAll("\\s*(\u001e|\u001c)", "").replaceAll("Co.{0,5}Ltd", "Co.\\\\, Ltd"));
		//System.out.println(bb.replaceAll("\\s*(\u001e|\u001c)", "").replaceAll("Co.{0,5}Ltd", "Co.\\\\, Ltd"));
		//System.out.println(bb.replaceAll("\\s*(\u001e|\u001c)", "").replaceAll("Co.{0,5}Ltd", "Co.\\\\,Ltd").replaceAll("C=CN,", "") + ",C=CN");
		String after = reviseDN(bb);
		System.out.println(after);
		System.out.println("\\a");
		getUnicode(bb);
		getUnicode(after);
		
		
		
/*		System.out.println(bb.replace("\u001e", "").replace("Co(.|)", "Co.\\,"));
		System.out.println(bb.replaceAll("\\s+(\u001e|\u001c)", ""));
		System.out.println(bb.replaceAll("\\s+(\u001e|\u001c)", ""));
		System.out.println(bb.replaceAll("\\s*(\u001e|\u001c)", ""));*/	
			
	}
	
	public static String reviseDN(String dn) {
		return dn.replaceAll("\\s*(\u001e|\u001c|C=CN,)", "").replaceAll("\\s*,\\s*", ",").replaceAll("Co.{0,5}Ltd", "Co.\\\\, Ltd") + ",C=CN";
	}
	
	public static void testCut() {
		long t = System.currentTimeMillis();
		int a = (int)(t - t/100*100);
		System.out.println("t:" + t);
		System.out.println("a:" + a);
	}
	
	public static void testAllIsChar() {
		System.out.println("aK".matches("[a-zA-Z]+"));
		System.out.println("aK1".matches("[a-zA-Z]+"));
		System.out.println("FffffFFF".matches("[fF]+"));
		System.out.println("ffffFFF".matches("f+"));
		System.out.println("ffffFFF".matches("[f]+"));
		System.out.println("fffffff".matches("f+"));
		System.out.println("fffffff".matches("[f]+"));
	}
	
	public static void testEngineNo() {
		//char a = '★';
		String keyWords = "*★";
		String ee = "JLB-4G13TB*H8CB0115163*";
		//String ee = "JLB-4G15★H8CA3309900★";
		
/*		for (int i = 0; i < keyWords.length(); i++) {
			System.out.println("keyWords["+ i + "]=" + (keyWords.charAt(i)));
		}*/
		
		int beginIndex = -1;
		int endIndex = -1;
		String enginNo = null;
		for (int i = 0; i < keyWords.length(); i++) {
			beginIndex = ee.indexOf(keyWords.charAt(i));
			if (beginIndex == -1) {
				continue;
			}
			beginIndex += 1;
			endIndex = ee.indexOf(keyWords.charAt(i), beginIndex);
			if (endIndex != -1) {
				enginNo = ee.substring(beginIndex, endIndex);
				break;
			}
			
			beginIndex = -1;
			endIndex = -1;
		}
		
		System.out.println("enginNo=" + enginNo);		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String a = "hello";
		String b = a;
		a = "world";
		System.out.println(a.substring(a.length()-1));
		System.out.println(b);
		testEngineNo();
		//testDelete();
		//testAllIsChar();
		//testContain();
		//testByte("ABCDE12345FGH");
		//testCut();
		//testReplace();
		//testChar();
		//testNewStringTest();
		//commontest();
		//testSubstring();
		//strTest();
		//strTest2("");

/*		byte[] tt = new byte[21];
		System.out.println(tt.length);
		 strTest();
		bigdecimalTest();

		splitTest();
		streamWriterTest();*/

	}
}
