package com.li.annotationtest;

import com.li.annotationtest.BeanAnnotation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanAnnotation extends UnitTestBase {

    public TestBeanAnnotation() {
        super("classpath*:annotationTestContext.xml");
    }

    @Test
    public void testSay() {
        BeanAnnotation bean = super.getBean("beanAnnotation");
        bean.say("This is test.");
    }

    @Test
    public void testScpoe() {
        BeanAnnotation bean = super.getBean("beanAnnotation");
        bean.myHashCode();
        bean = super.getBean("beanAnnotation");
        bean.myHashCode();
    }

}