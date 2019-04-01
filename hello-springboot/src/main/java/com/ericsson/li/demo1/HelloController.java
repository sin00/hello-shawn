package com.ericsson.li.demo1;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class HelloController {
//the param 'name' in temple hello1.html must be consistent with {name}
    @Autowired
    private List<Hi> hiList;

    @Autowired
    OtherMapList OtherMapList;

    @Autowired
    private Map<String, Hi> hiMap;
    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello1";
    }
    
    @RequestMapping("/hi")
    public String hi() {
    	String msg = "";
        for (Hi hi : hiList) {
        	String tmp = hi.sayHi();
        	System.out.println(tmp);
        	msg = msg + tmp + "\n";
        }
        return msg;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(HelloController.class, args);
    }
}
