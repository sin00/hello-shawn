package com.ericsson.li.dubbo.provider;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {
	public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext ("dubbo-provider.xml");
        context.start ();
        try {
            System.in.read ();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        }// 按任意键退出
    }
}
