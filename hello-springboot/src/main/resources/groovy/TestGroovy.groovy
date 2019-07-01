package com.li.groovy


import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
 
/**
 * groove class
 */
class TestGroovy {
 
    void print() {
        System.out.println("hello word!!!!");
    }
 
    List<String> printArgs(String str1, String str2, String str3) {
        String jsonString = "[\""+str1+"\",\""+str2+"\",\""+str3+"\"]";
        return JSON.parseObject(jsonString, new TypeReference<List<String>>() {});
    }
 
}