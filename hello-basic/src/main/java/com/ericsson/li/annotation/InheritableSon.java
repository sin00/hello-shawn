package com.ericsson.li.annotation;

/**
 * @Inherited 演示示例
 * 
 * @author skywang
 * @email kuiwu-wang@163.com
 */

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Inherited;

/**
 * 自定义的Annotation。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//@Inherited //注释掉“Inheritable的@Inherited注解”。然后比较
@interface Inheritable1
{
}

@Inheritable1
class InheritableFather
{
    public InheritableFather() {
        // InheritableBase是否具有 Inheritable Annotation
        System.out.println("InheritableFather:"+InheritableFather.class.isAnnotationPresent(Inheritable1.class));
    }
}

/**
 * InheritableSon 类只是继承于 InheritableFather，
 */
public class InheritableSon extends InheritableFather
{
    public InheritableSon() {
        super();    // 调用父类的构造函数
        // InheritableSon类是否具有 Inheritable Annotation
        System.out.println("InheritableSon:"+InheritableSon.class.isAnnotationPresent(Inheritable1.class));
    }
    
    public static void main(String[] args)
    {
        InheritableSon is = new InheritableSon();
    }
}
