package com.ericsson.li.script;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.StringBuilderWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class Function {
	//public static final TimeZone UTC = TimeZone.getTimeZone("UTC");
	
	public static Object timestamp(String value, String formatFrom) throws ParseException {
		checkStringNotEmpty(value,formatFrom);
		DateFormat dateFormat = new SimpleDateFormat(formatFrom);
		//dateFormat.setTimeZone(UTC);
		Date date = dateFormat.parse(value);
		return date.getTime();
	}

	public static Object converttimeformat(String value, String formatFrom, String formatTo) throws ParseException {
		checkStringNotEmpty(value,formatFrom,formatTo);
		
		DateFormat dateFormat = new SimpleDateFormat(formatFrom);
		Date date = dateFormat.parse(value);
		
		dateFormat = new SimpleDateFormat(formatTo);
//		dateFormat.setTimeZone(UTC);
		
		return dateFormat.format(date);
	}


	public static Object converttimeformat(String dates,String time, String formatFrom, String formatTo) throws ParseException {
		String value=dates+time;
//		checkStringNotEmpty(value,formatFrom,formatTo);
//		
//		DateFormat dateFormat = new SimpleDateFormat(formatFrom);
//		Date date = dateFormat.parse(value);
//		
//		dateFormat = new SimpleDateFormat(formatTo);
//		dateFormat.setTimeZone(UTC);
		
//		return dateFormat.format(date);
		return converttimeformat(value,formatFrom,formatTo);
	}
	
	
	public static Object timestamp() {
		return Calendar.getInstance().getTime().getTime();
	}

	public static Object timestamp(String dates,String time,String formatFrom) throws ParseException {
		String value=dates+time;
		checkStringNotEmpty(dates,time,formatFrom);
		
		DateFormat dateFormat = new SimpleDateFormat(formatFrom);
		//dateFormat.setTimeZone(UTC);
		Date date = dateFormat.parse(value);
		return date.getTime();
	}
	
	public static Object random(String min, String max) {
		checkStringNotEmpty(min,max);
		int iMin = Integer.valueOf(min);
		int iMax = Integer.valueOf(max);
		Random rand = new Random();
		int randomNum = rand.nextInt((iMax - iMin) + 1) + iMin;
		return randomNum;
	}
	
	public static Object getfrontfieldnumval(String arg1, String arg2, String arg3, String arg4, String arg5){
		String result = null;		
		String[] args = new String[]{arg1,arg2,arg3,arg4,arg5};
		
		for (int i = 0; i < args.length; i++) {
			if(args[i]!=null && !"".equals(args[i].trim())){
				double dval = Double.valueOf(args[i].trim());
				if(dval>0.0){
					result = args[i].trim();
					break;
				}
			}
		}
		checkStringNotEmpty(result);
		
		return result;
	}
	
	public static Object getfrontfieldstrval(String arg1, String arg2, String arg3){
		String result = null;		
		String[] args = new String[]{arg1,arg2,arg3};
		
		for (int i = 0; i < args.length; i++) {
			if(args[i]!=null && !"".equals(args[i].trim())){
				result = args[i].trim();
				break;
			}
		}
		checkStringNotEmpty(result);
		
		return result;
	}
	
	private static void checkStringNotEmpty(String... args){
		if(args==null){
			throw new NullPointerException("[FIELD_MISSING]");
		}
		
		for (int i = 0; i < args.length; i++) {
			if(args[i]==null || "".equals(args[i].trim()) 
			        || args[i].contains("[FIELD_MISSING]")){
				throw new NullPointerException("[FIELD_MISSING]");
			}
		}
	}
	
	public static Object dualoperation(String arg1, String arg2, String oper, String dataType, String precision){
	    String result = null;
	    
	    checkStringNotEmpty(arg1, arg2, oper, dataType);
	    
	    Double operand1D = Double.valueOf(arg1);
        Double operand2D = Double.valueOf(arg2);
        Double value = 0.0;
        
        if("Plus".equalsIgnoreCase(oper)){//+
            value = operand1D + operand2D;
        }else if("Minus".equalsIgnoreCase(oper)){//-
            value = operand1D - operand2D;
        }else if("Multiply".equalsIgnoreCase(oper)){//*
            value = operand1D * operand2D;
        }else if("Divide".equalsIgnoreCase(oper)){
            if(operand2D<=0.0){
                checkStringNotEmpty(result);
                value = 0.0;
            }else{
            	value = operand1D / operand2D;
            }
        }
	    
	    if("Double".equalsIgnoreCase(dataType)){
	        int bit = Integer.valueOf(precision).intValue();
	        if(bit>0){
	            String format = "%." + bit + "f";
	            result = String.format(format, value);
	        }else{
	            result = value.toString();
	        }
	    }else if("Long".equalsIgnoreCase(dataType)){
	        result = value.longValue()+"";
	    }
	    
	    return result;
	}
	
	public static Object setvaluebycondition(String cond, String ifValue, String elseValue){
	    String result = null;
	    
	    checkStringNotEmpty(cond);
	    
	    if(cond!=null && !"".equals(cond.trim())){
            double dval = Double.valueOf(cond.trim());
            if(dval>0.0){
                result = ifValue;
            }else{
                result = elseValue;
            }
        }
	    
	    return result;
	}


	
	public static Object convertdateformat(String dayOfYear, String timeOfDay,String formatFrom, String formatTo) throws ParseException {
		checkStringNotEmpty(dayOfYear,timeOfDay,formatFrom,formatTo);
		
		DateFormat dateFormat = new SimpleDateFormat(formatFrom);
		Date date = dateFormat.parse(dayOfYear + timeOfDay);
		
		dateFormat = new SimpleDateFormat(formatTo);
		TimeZone tz = TimeZone.getTimeZone("UTC");
		dateFormat.setTimeZone(tz);
		
		return dateFormat.format(date);
	}
	
	public static Object convertbyte(byte[] b, String fieldtype, String sSize) {
		if (b == null) {
			throw new NullPointerException("[FIELD_MISSING]");
		}

		int size = Integer.valueOf(sSize);
		size = b.length < size ? b.length : size;
		if ("integer".equals(fieldtype)) {
			return byte2Int(b, size);
		} else if ("string".equals(fieldtype)) {
			return byte2String(b, size);
		} else if ("boolean".equals(fieldtype)) {
			return byte2Boolean(b, size);
		}
		throw new NullPointerException("[FIELD_MISSING]");
	}

	
	private static boolean byte2Boolean(byte[] b, int size) {
		return b[0] != 0;
	}

	private static int byte2Int(byte[] b, int size) {
		int result = 0;
		for (int i = 0; i < size; i++) {
			result = result << 8;
			result += b[i] & 0xff;
		}
		return result;

	}

	private static String byte2String(byte[] b, int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append((char) (b[i] & 0xff));
		}
		return sb.toString();
	}

	public static String bytesToHexString(byte[] b){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; ++i){
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}
			sb.append(s);
		}
		return sb.toString();
	}


	public static String buildFilePath(String key, String suffix) {
		return "template/" + key + "." + suffix;
	}

	public static String readFile(String filePath) throws IOException {
		//return Resources.toString(Resources.getResource(filePath), StandardCharsets.UTF_8);
		ClassLoader classLoader = Function.class.getClassLoader();
		final URL url = classLoader == null ? IOUtils.class.getResource(filePath) : classLoader.getResource(filePath);
		try (InputStream inputStream = url.openStream()) {
			try (final StringBuilderWriter sw = new StringBuilderWriter()) {
				final InputStreamReader in = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
				IOUtils.copy(in, sw);
				return sw.toString();
			}
		}
	}

	public static boolean hasText(String string) {
		if (null == string || string.trim().length() == 0) {
			return false;
		}
		return true;
	}
}
