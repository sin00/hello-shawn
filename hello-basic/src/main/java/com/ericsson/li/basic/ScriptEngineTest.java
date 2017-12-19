package com.ericsson.li.basic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineTest {

	public static void test1() throws ScriptException {
		String str = "(a >= 0 && a <= 5)";
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.put("a", 4);
		Object result = engine.eval(str);
		System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);
	}

	public static void test2() throws ScriptException {
		//String str = "43*(2 + 1.4)+2*32/(3-2.1)";
		String str = "0x3ff&0x03FF/1*1/10";
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		Object result = engine.eval(str);
		System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);
		System.out.println("ooo:" + (0x03ff&0x03FF)/1*1/10);
		System.out.println(0x03ff&0x03FF/1*1/10);	
	}
	
	public static void test3() throws ScriptException {
		//String filePath = "c:/workspace/test/rms-carcom/back/outDIDItemFormula.1477641354596无重复";
		String filePath = "c:/workspace/test/rms-carcom/outDIDItemFormula.1477885016518";

		BufferedReader br = null;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		String beforString = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String str = line.trim();
				if (str.substring(0, 1).equals("[")) {
					beforString = str;
					continue;
				}
				if (str.contains("X")) {
					engine.put("X", 4);
				}

				try {
					Object result = engine.eval(str);
					if (result != null) {
						//System.out.println("Formula[ " + str + "], Result-ObjectName[" + result.getClass().getName() + "], Result[" + result + "]");
					} else {
						//System.out.println("Formula[ " + str + "], Result[null]");
					}
				} catch (Exception e) {
					//System.out.println("Formula[ " + str + " ], excepiont[" + e.getMessage() + "]");
					System.out.println("key: " + beforString);
					System.out.println("Formula: " + str);
				}
				
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public static void test4() throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");	
		System.out.println("ccc:" + engine.eval(""));
		String aaa = null;
		System.out.println("aaa:" + engine.eval(aaa));
		
	}
	public static void main(String[] args) throws ScriptException {
		//test1();
		//test2();
		test3();
		//test4();
		
	}
}
