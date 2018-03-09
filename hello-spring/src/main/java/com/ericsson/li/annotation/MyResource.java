package com.ericsson.li.annotation;

import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;  
  
/** 
 * @Description:定义注解 
 * @ClassName: ZxfResource 
 * @Project: spring-aop 
 * @Author: zxf 
 * @Date: 2011-6-7 
 */  
// 在运行时执行  
@Retention(RetentionPolicy.RUNTIME)  
// 注解适用地方(字段和方法)  
@Target({ ElementType.FIELD, ElementType.METHOD })  
public @interface MyResource {  
  
    //注解的name属性  
    public String name() default "";  
} 