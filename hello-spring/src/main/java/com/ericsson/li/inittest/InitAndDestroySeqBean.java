package com.ericsson.li.inittest;

import javax.annotation.PostConstruct;  
import javax.annotation.PreDestroy;  
  
import org.springframework.beans.factory.DisposableBean;  
import org.springframework.beans.factory.InitializingBean;  
import org.springframework.context.support.ClassPathXmlApplicationContext;  
  
public class InitAndDestroySeqBean implements InitializingBean,DisposableBean {  
  
    public InitAndDestroySeqBean(){  
        System.out.println("执行InitAndDestroySeqBean: 构造方法");  
    }  
      
    @PostConstruct  
    public void postConstruct() {    
       System.out.println("执行InitAndDestroySeqBean: postConstruct");    
    }    
      
    @Override  
    public void afterPropertiesSet() throws Exception {  
        System.out.println("执行InitAndDestroySeqBean: afterPropertiesSet");   
    }  
      
    public void initMethod() {  
        System.out.println("执行InitAndDestroySeqBean: init-method");  
    }  
  
    @PreDestroy  
    public void preDestroy()  {  
        System.out.println("执行InitAndDestroySeqBean: preDestroy");  
    }  
      
    @Override  
    public void destroy() throws Exception {  
        System.out.println("执行InitAndDestroySeqBean: destroy");  
    }  
      
    public void destroyMethod() {  
        System.out.println("执行InitAndDestroySeqBean: destroy-method");  
    }  
      
    public static void main(String[] args) {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-inittest.xml");  
        context.close();  
    }  
}  