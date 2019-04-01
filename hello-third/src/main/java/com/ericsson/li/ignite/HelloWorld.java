package com.ericsson.li.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class HelloWorld {
    public static void main(String[] args){
        // 使用默认配置启动
        try(Ignite ignite = Ignition.start()){
            ignite.compute().broadcast(() -> System.out.println("hello world"));
        }
    }
}
