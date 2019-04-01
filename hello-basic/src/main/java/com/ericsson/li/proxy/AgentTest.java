package com.ericsson.li.proxy;

import java.lang.reflect.Proxy;

public class AgentTest {
    
    public static void main(String[] args) {
      //  if Singer did not implements from Person. then  Exception in thread "main" java.lang.ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to com.ericsson.li.proxy.Singer
/*    	Singer singer = new Singer("刘德华");
       
       SingerAgent singerAgent = new SingerAgent(singer);
       singer = (Singer)Proxy.newProxyInstance(singerAgent.getClass().getClassLoader(), singer
               .getClass().getInterfaces(), singerAgent);
        //调用代理对象的sing方法
        String retValue = singer.sing("冰雨");
        System.out.println(retValue);
        //调用代理对象的dance方法
        String value = singer.dance("江南style");
        System.out.println(value);*/
    	
    	Singer singer = new Singer("刘德华");
        
        SingerAgent singerAgent = new SingerAgent(singer);
        Person person = (Person)Proxy.newProxyInstance(singer.getClass().getClassLoader(), singer
                .getClass().getInterfaces(), singerAgent);
         //调用代理对象的sing方法
         String retValue = person.sing("冰雨");
         System.out.println(retValue);
         //调用代理对象的dance方法
         String value = person.dance("江南style");
         System.out.println(value);
    }
}
