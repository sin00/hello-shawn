package com.ericsson.li.type;

import java.math.BigInteger;

public class CastTest {
    public static void main(final String[] args) {
/*    	long i = 9000000000L;
    	while((i--) > 0);*/
    	//String a = "";
    	//System.out.println(a.equals(null));
    	//System.out.println(a.equals(""));
       	//unicodeTest();
       	//unicodeTest2();
    	//unicodeTest3();
       	commonTest();
    }
    
    public static void commonTest() {

        int intValue = Integer.MAX_VALUE;
        long longValue = Long.MAX_VALUE;
        float floatValue = Float.MAX_VALUE;
 
        System.out.println("Integer.BYTES:" + Integer.BYTES);
        System.out.println("Long.BYTES:" + Long.BYTES);
        System.out.println("Float.BYTES:" + Float.BYTES);
        
        System.out.println("intValue:" + intValue);
        System.out.println("longValue:" + longValue);
        System.out.println("floatValue:" + floatValue);
        
        // 大类型向小类型转换，要么编译出错，要么在一定情况下溢出
        // intValue = longValue;   // 编译出错，提示从long型转换为int型时会丢失精度
        intValue = (int)longValue; // 正常编译，由程序员自己负责，即使丢失精度也要强制转换
        System.out.println("intValue:" + intValue); // 强制转换的结果为错误值：-1
        intValue = Integer.MAX_VALUE;
        
        // 小类型向大类型转换，不会发生问题
        longValue = intValue;
        System.out.println("longValue:" + longValue); // 正常输出2147483647
        
        // 大类型向小类型转换，要么编译出错，要么在一定情况下溢出
        // longValue = floatValue;    // 编译出错，提示从float型转换为long型时会丢失精度
        longValue = (long)floatValue; // 正常编译，由程序员自己负责，即使丢失精度也要强制转换
        System.out.println("longValue:" + longValue); // 强制转换的结果为错误值：9223372036854775807
        longValue = Long.MAX_VALUE;
        
        // 小类型向大类型转换，不会发生问题
        floatValue = longValue;
        System.out.println("floatValue:" + floatValue); // 正常输出9.223372E18
    
    }
    
    public static void unicodeTest() {
		String a = "1e";
		String b = "\u001e";
		int n = a.length();
		int code = 0;
		for (int i = 0; i < n; i++) {
			code = code * 16 + Integer.parseInt(a.substring(i, i + 1), 16);
		}
		String c = String.valueOf((char) code);
		System.out.println("a:" + a);
		System.out.println("b:" + b);
		System.out.println("c:" + c);
		System.out.println(b.equals(c));
		
    	//String hexString = "5A64AA8C95A3137493FFF21544DFCF3AC0C44A94";
    	/*String hexString = "5A64AA8C95A31374";
    	System.out.println(hexString + ":" + new BigInteger(hexString, 16).toString());
    	hexString = "5A64AA8C95A3137493FFF21544DFCF3AC0C44A94";
    	System.out.println(hexString + ":" + new BigInteger(hexString, 16).toString());*/
    }
    
    public static String string2Unicode(String string) {
    	 
        StringBuffer unicode = new StringBuffer();
     
        for (int i = 0; i < string.length(); i++) {
     
            // 取出每一个字符
            char c = string.charAt(i);
     
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
     
        return unicode.toString();
    }
    
    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {
     
        StringBuffer string = new StringBuffer();
     
        String[] hex = unicode.split("\\\\u");
     
        for (int i = 1; i < hex.length; i++) {
     
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
     
            // 追加成string
            string.append((char) data);
        }
     
        return string.toString();
    }
    
    public static void unicodeTest2() {
        String test = "最代码网站地址:www.zuidaima.com";     
        String unicode = string2Unicode(test);         
        String string = unicode2String(unicode) ;
         
        System.out.println(unicode);         
        System.out.println(string);
        
        unicode = "\u001e";
        string = unicode2String(unicode) ;
        System.out.println(unicode);         
        System.out.println(string);
    }
    
    public static void unicodeTest3() {

        
        String unicode = "ABC\u001cDEF";
        String string = unicode.replace("\u001c", "");
        if (unicode.equals(string)) {
        	System.out.println("1equal");
        } else {
        	System.out.println("1unequal");
        }
        
        if ("ABCDEF".equals(string)) {
        	System.out.println("2equal");
        } else {
        	System.out.println("2unequal");
        }
        
        System.out.println("unicode:" + unicode);         
        System.out.println("string:" + string);
        
        String sdn = "O=Zhejiang Geely Holding Group Co.\u001e Ltd";
        String rdn = "CN=       \u001cGeely Code Signin\u001cg Service for Web based App Production";
        String srdn = "O=Zhejiang Geely Holding Group Co.\u001e Ltd, CN= \u001cGeely Code Signing Service for Web based App Production";
        System.out.println("sdn:" + sdn.replace("\u001e", "\\,"));
        System.out.println("rdn:" + rdn.replace("\u001c", ""));
        System.out.println("srdn:" + srdn.replace("\u001e", "\\,").replace("\u001c", ""));
        System.out.println("rdn:" + rdn.replaceAll("\\s+\u001c", ""));
    }
}
