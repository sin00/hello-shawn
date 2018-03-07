package com.ericsson.li.javassist;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

public class TestMain {
	
	public static void main(String[] args) throws Exception {
		testCreateClass();
		testCreateClass2();
		testCreateClass3();
	}
	
	public static void testCreateClass() throws NotFoundException, CannotCompileException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String clazzName = "com.ericsson.li.javassist.JavassistTest";

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(clazzName);

        //添加私有成员name，添加getter/setter
        CtField field = new CtField(pool.get(String.class.getName()), "name", ctClass);
        field.setModifiers(Modifier.PRIVATE);
        ctClass.addMethod(CtNewMethod.setter("setName", field));
        ctClass.addMethod(CtNewMethod.getter("getName", field));
        ctClass.addField(field);

        //添加有参构造方法
        CtConstructor cons = new CtConstructor(new CtClass[]{pool.get(String.class.getName())}, ctClass);
        cons.setBody("{$0.name = $1;}");
        ctClass.addConstructor(cons);

        //添加execute方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        StringBuffer body = new StringBuffer();
        body.append("{\n System.out.println(name);");
        body.append("\n System.out.println(\"execute ok\");");
        body.append("\n return ;");
        body.append("\n}");
        ctMethod.setBody(body.toString());
        ctClass.addMethod(ctMethod);

        //生成class
        Class<?> c = ctClass.toClass();

        //通过有参构造方法实例化
        Object target = c.getConstructor(String.class).newInstance("hugo");
        Method method = target.getClass().getMethod("execute", new Class[]{});
        method.invoke(target, new Object[]{});
    }
	
	public static void testCreateClass2() throws CannotCompileException, IOException, NotFoundException {

        ClassPool pool = ClassPool.getDefault();

        //定义类
        CtClass stuClass = pool.makeClass("com.ericsson.li.javassist.JavassistTest1");

        //加载类
//      CtClass cc =  pool.get(classname);

        //id属性
        CtField idField = new CtField(CtClass.longType, "id", stuClass);
        stuClass.addField(idField);

        //name属性
        CtField nameField = new CtField(pool.get("java.lang.String"), "name", stuClass);
        stuClass.addField(nameField);

        //age属性
        CtField ageField = new CtField(CtClass.intType, "age", stuClass);
        stuClass.addField(ageField);

        CtMethod getMethod = CtNewMethod.make("public int getAge() { return this.age;}", stuClass);
        CtMethod setMethod = CtNewMethod.make("public void setAge(int age) { this.age = age;}", stuClass);

        stuClass.addMethod(getMethod);
        stuClass.addMethod(setMethod);

//        stuClass.writeFile("F:\\Practice_Demo");

        Class<?> clazz = stuClass.toClass();
        System.out.println("class:"+clazz.getName());

        System.out.println("------------属性列表------------");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType()+"\t"+field.getName());
        }

        System.out.println("------------方法列表------------");
        //方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method: methods){
            System.out.println(method.getReturnType()+"\t"+method.getName()+"\t"+Arrays.toString(method.getParameterTypes()));
        }

    }
	
    public static void testCreateClass3() throws CannotCompileException, IOException, NotFoundException, InstantiationException, IllegalAccessException {

        ClassPool pool = ClassPool.getDefault();

        // 定义类
        CtClass ctClass = pool.get("com.ericsson.li.javassist.Calculator");

        // 需要修改的方法名称
        String mname = "getSum";
        CtMethod mold = ctClass.getDeclaredMethod(mname);
        // 修改原有的方法名称
        String nname = mname + "$impl";
        mold.setName(nname);

        //创建新的方法，复制原来的方法
        CtMethod mnew = CtNewMethod.copy(mold, mname, ctClass, null);
        // 主要的注入代码
        StringBuffer body = new StringBuffer();
        body.append("{\nlong start = System.currentTimeMillis();\n");
        // 调用原有代码，类似于method();($$)表示所有的参数
		body.append(nname + "($$);\n");
        body.append("System.out.println(\"Call to method " + mname
                + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");

        body.append("}");
        // 替换新方法
        mnew.setBody(body.toString());
        // 增加新方法
        ctClass.addMethod(mnew);

        Calculator calculator =(Calculator)ctClass.toClass().newInstance(); 

        calculator.getSum(10000);
    }
}

class Calculator {

    public void getSum(long n) {
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        System.out.println("n="+n+",sum="+sum);
    }
}
