package com.ericsson.li.lamda;

public class Main {

	public static void test(MyInterface myInterface) {
		//myInterface.lMethod();
		//myInterface.lMethod("Hello World!");
        int result=myInterface.lMethod(3,2);//设置参数内容,接收返回参数
        System.out.println(result);
	}

	public static void main(String[] args) {
		//test(() -> System.out.println("Hello World!"));
/*        test((s)->{
            s=s+s;
            System.out.println(s);
        });*/
		
		test((x,y)-> x*y );//调用方法
	}
}
