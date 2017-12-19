package com.ericsson.li.junit;

import junit.framework.TestCase;

public class JUnitTest extends TestCase {  
    @Override  
    protected void setUp() throws Exception {  
  
        System.out.println("做一些前提条件的设置");  
          
    }  
    @Override  
    protected void tearDown() throws Exception {  
        System.out.println("释放一些资源");  
          
    }  
    public void testSomething1(){  
        System.out.println("执行单元测试testSomething1");  
    }  
    public void testSomething2(){  
        System.out.println("执行单元测试testSomething2");  
    }  
}  