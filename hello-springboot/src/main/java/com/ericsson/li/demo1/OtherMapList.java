package com.ericsson.li.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Component
public class OtherMapList {
//the param 'name' in temple hello1.html must be consistent with {name}
    @Autowired
    private List<Hi> hiList;

    @Autowired
    private Map<String, Hi> hiMap;

}
