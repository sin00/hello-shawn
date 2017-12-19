package com.ericsson.li.dubbo.provider;

import org.springframework.stereotype.Service;

@Service(value = "provideService")
public class ProvideServiceImpl implements ProvideService {

    public String sayHello(String name){
        System.out.println ("ProvideServiceImpl  sayHello 走到了");
        return name + " say Hello";
    }

}
