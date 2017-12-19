package com.ericsson.li.basic;

public class MethodName {
  
		public static void test1() {
		    // 方法1：通过SecurityManager的保护方法getClassContext()  
		    String clazzName = new SecurityManager() {  
		        public String getClassName() {  
		            return getClassContext()[1].getName();  
		        }  
		    }.getClassName();  
		    System.out.println(clazzName);  
		    // 方法2：通过Throwable的方法getStackTrace()  
		    String clazzName2 = new Throwable().getStackTrace()[1].getClassName();  
		    System.out.println(clazzName2);  
		    // 方法3：通过分析匿名类名称()  
		    String clazzName3 = new Object() {  
		        public String getClassName() {  
		            String clazzName = this.getClass().getName();  
		            return clazzName.substring(0, clazzName.lastIndexOf('$'));  
		        }  
		    }.getClassName();  
		    System.out.println(clazzName3);  
		    //方法4：通过Thread的方法getStackTrace()  
		    String clazzName4 = Thread.currentThread().getStackTrace()[2].getClassName();  
		    System.out.println(clazzName4); 
		}
		
		public static void test2() {
			MethodName methodName = new MethodName();   
			  String clazz = Thread.currentThread() .getStackTrace()[1].getClassName();
			 String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
			 System.out.println(methodName.getClass().getName());
			 System.out.println("class name: " + clazz + " Method Name: " + method); 
			  methodName.anotherMethod(); }   
			  private void anotherMethod() { 
			  String clazz = this.getClass().getName(); 
			  String method = Thread.currentThread() .getStackTrace()[1].getMethodName(); 
			  System.out.println("class name: " + clazz + " Method Name: " + method);
			  System.out.println("file name: " + Thread.currentThread() .getStackTrace()[1].getFileName());
		}
			  
		public static void main(String[] args) { 
		     //test1();
		     test2();
		  }   
}
