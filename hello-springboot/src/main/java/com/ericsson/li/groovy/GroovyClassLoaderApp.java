package com.ericsson.li.groovy;

import groovy.lang.*;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GroovyClassLoaderApp {

	private static GroovyClassLoader groovyClassLoader = null;

	public static void initGroovyClassLoader() {
		CompilerConfiguration config = new CompilerConfiguration();
		config.setSourceEncoding("UTF-8");
		// 设置该GroovyClassLoader的父ClassLoader为当前线程的加载�?(默认)
		groovyClassLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
	}

	public static void main(String[] args) {
		 //loadClass();
		System.out.println("===========loadFile===========");
		loadFile();
		
		System.out.println("============testGroovyShellHellow==========");
		testGroovyShellHellow();
		System.out.println("============evaluate1==========");
		evaluate1();

        try {
            evaluate3();
            System.out.println("==============evaluate3-end=====================");
            evaluate4();
            System.out.println("==============evaluate4-end=====================");
            evaluate5();
            System.out.println("==============evaluate5-end=====================");
            evaluate6();
            System.out.println("==============evaluate6-end=====================");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * 通过类加载groovy
	 */
	private static void loadClass() {
		GroovyObject groovyObject = null;
		try {
			groovyObject = (GroovyObject) GroovyClassLoaderApp.class.getClassLoader()
					.loadClass("com.ericsson.li.groovy.TestGroovy").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 执行无参函数
		groovyObject.invokeMethod("print", null);

		System.out.println("============================");

		// 执行有参函数
		Object[] objects = new Object[] { "abc", "def", "ghi" };
		List<String> ls = (List<String>) groovyObject.invokeMethod("printArgs", objects);
		ls.stream().forEach(System.out::println);
	}

	/**
	 * 通过文件路径加载groovy
	 * 
	 * @return
	 */
	private static boolean loadFile() {
		File groovyFile = new File("groovy/TestGroovy.groovy");
		if (!groovyFile.exists()) {
			System.out.println("文件不存�?");
			return false;
		}

		initGroovyClassLoader();

		try {
			List<String> result;
			// 获得TestGroovy加载后的class
			Class<?> groovyClass = groovyClassLoader.parseClass(groovyFile);
			// 获得TestGroovy的实�?
			GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
			// 反射调用printArgs方法得到返回�?
			Object methodResult = groovyObject.invokeMethod("printArgs", new Object[] { "chy", "zjj", "xxx" });
			if (methodResult != null) {
				result = (List<String>) methodResult;
				result.stream().forEach(System.out::println);
			}
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static void testGroovyShellHellow(){
		try {
			Binding binding = new Binding();
	    	binding.setProperty("foo", new Integer(2));
	    	GroovyShell shell = new GroovyShell(binding);
	    	Object value = shell.evaluate(new File("src/main/groovy/GroovyShellHellow.groovy"));
	    	System.out.println(value);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}
	

	public static void evaluate1(){
        String type = "List<String>";
        String jsonString = "[\"wei.hu\",\"mengna.shi\",\"fastJson\"]";
 
        Binding binding = new Binding();
        binding.setProperty("jsonString", jsonString);
        binding.setProperty("type", type);
        GroovyShell groovyShell = new GroovyShell(binding);
 
        // todo 必须引用 import 否则会出�?
        String groovyStr =  "import com.alibaba.fastjson.JSON;\n" +
                        "import com.alibaba.fastjson.TypeReference;\n" +
                        "TypeReference<"+ type +"> typeReference = new TypeReference<" + type +">(){};\n" +
                        "JSON.parseObject(jsonString, typeReference);";
        System.out.println(groovyStr);
        Object o =  groovyShell.evaluate(groovyStr);
        System.out.println(o.getClass().getName() + ":" + o);
        
    }

	//自动执行有参数的函数
	public static void evaluate3() throws CompilationFailedException, IOException {
		Binding binding = new Binding();
		// 和参数名称一�?
		binding.setProperty("arg", "chy");
		GroovyShell groovyShell = new GroovyShell(binding);
		Object result = groovyShell.evaluate(new File("src/main/groovy/FunArgGroove.groovy"));
		System.out.println(result.toString());
	}
	//自动执行无参数的函数
	public static void evaluate4() throws CompilationFailedException, IOException {
		Binding binding = new Binding();
		GroovyShell groovyShell = new GroovyShell(binding);
		Object result = groovyShell.evaluate(new File("src/main/groovy/FunGroove.groovy"));
	}
	//动�?�执行无参数的函�?
    public static void evaluate5() throws CompilationFailedException, IOException {
        GroovyShell groovyShell = new GroovyShell();
        Script script= groovyShell.parse(new File("src/main/groovy//ScriptGroove.groovy"));
        Object result = script.invokeMethod("print",null);
    }
  //动�?�执行有参数的函�?
	public static void evaluate6() throws CompilationFailedException, IOException {
        GroovyShell groovyShell = new GroovyShell();
        Script script= groovyShell.parse(new File("src/main/groovy/ScriptGroove.groovy"));
        Object[] args = {"1","2","3"};
        Object result = script.invokeMethod("printArgs",args);
        if(null!=result) {
            List<String> ls = (List<String>)result;
            ls.stream().forEach(System.out::println);
        }
	}
        
}
