package com.ericsson.li.groovy
String printArg(String name){
    System.out.println(":"+name);
    return "result:"+name;
}

printArg(arg);