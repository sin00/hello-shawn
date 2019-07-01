package com.li.groovy
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference

void hh(Map<Object, Object> map, String a) {

}
 
void print(){
    System.out.println("no params!!!!");
}
 
List<String> printArgs(String str1, String str2, String str3) {
    String jsonString = "[\""+str1+"\",\""+str2+"\",\""+str3+"\"]";
    return JSON.parseObject(jsonString, new TypeReference<List<String>>() {});
}
