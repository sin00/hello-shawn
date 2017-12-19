package com.ericsson.li.format.jacson;

import java.io.IOException;  
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.Date;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;

//import javax.persistence.MapsId;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;  
  
public class JacksonStudent {  
  
    public static void main(String[] args) throws IOException {  
        //test1(); 
    	//testVin();
    	testParms2();
    }
    
    private static void testParms2() throws JsonProcessingException, IOException, JsonParseException, JsonMappingException {
    	ObjectMapper mapper = new ObjectMapper();

       	long eTime = new Date().getTime();
    	DateTime dte = new DateTime(eTime);	
    	Map<String, Object> startTime = new HashMap<String, Object>(); 
    	startTime.put("to", dte.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    	
    	Map<String, Object> endTime = new HashMap<String, Object>(); 
    	endTime.put("from", dte.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
 
    	String vin1 = "vin1";
    	String vin2 = "vin2";
    	String[] vinIds = {vin1, vin2};       
        
        
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("startTime", startTime);
        body.put("endTime", endTime);
        body.put("vehicleId", vinIds);
        
        String jsonfromArr =  mapper.writeValueAsString(body);
        System.out.println(jsonfromArr);  
    }
    
    private static void testParms() throws JsonProcessingException, IOException, JsonParseException, JsonMappingException {
    	ObjectMapper mapper = new ObjectMapper();

    	long sTime = new Date().getTime();
       	long eTime = new Date().getTime();
    	DateTime dts = new DateTime(sTime);
    	DateTime dte = new DateTime(eTime);	
    	Map<String, Object> endTime = new HashMap<String, Object>(); 
    	endTime.put("from", dts.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    	endTime.put("to", dte.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
 
    	String vin1 = "vin1";
    	String vin2 = "vin2";
    	String[] vinIds = {vin1, vin2};       
        
        
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("endTime", endTime);
        body.put("vehicleId", vinIds);
        
        String jsonfromArr =  mapper.writeValueAsString(body);
        System.out.println(jsonfromArr);  
    }
    
    private static void testVin() throws JsonProcessingException, IOException, JsonParseException, JsonMappingException {
    	ObjectMapper mapper = new ObjectMapper();
    	String vin1 = "vin1";
    	String vin2 = "vin2";
    	String[] vinIds = {vin1, vin2};
        String jsonfromArr =  mapper.writeValueAsString(vinIds);  
        System.out.println(jsonfromArr);   
        String[] strArr2 =  mapper.readValue(jsonfromArr, String[].class);  
        System.out.println(Arrays.toString(strArr2));  
        
        Map<String, Object> mapVin =  new HashMap<String, Object>();
        mapVin.put("vehicleId", vinIds);
        jsonfromArr =  mapper.writeValueAsString(mapVin);
        System.out.println(jsonfromArr);  
        Map<String, Object> mapVin2 = mapper.readValue(jsonfromArr, Map.class); 
        //System.out.println(Map);
    }

	private static void test1() throws JsonProcessingException, IOException, JsonParseException, JsonMappingException {
		Student student1 = new Student();    
        student1.setId(5237);  
        student1.setName("jingshou");  
        student1.setBirthDay(new Date());  
          
        Student student3 = new Student();    
        student3.setId(5117);    
        student3.setName("saiya");    
        student3.setBirthDay(new Date());    
          
        ObjectMapper mapper = new ObjectMapper();  
            
        //Convert between List and JSON  
        List<Student> stuList = new ArrayList<Student>();  
        stuList.add(student1);  
        stuList.add(student3);  
        String jsonfromList = mapper.writeValueAsString(stuList);  
        System.out.println(jsonfromList);  
          
        //List Type is not required here.  
        List stuList2 = mapper.readValue(jsonfromList, List.class);  
        System.out.println(stuList2);      
        System.out.println("************************************1");  
          
        //Convert Map to JSON  
        Map<String, Object> map = new HashMap<String, Object>();  
        map.put("studentList", stuList);  
        map.put("class", "ClassName");  
        String jsonfromMap =  mapper.writeValueAsString(map);  
        System.out.println(jsonfromMap);  
          
        Map map2 =  mapper.readValue(jsonfromMap, Map.class);  
        System.out.println(map2);  
        System.out.println(map2.get("studentList"));      
        System.out.println("************************************2");     
          
        //Convert Array to JSON  
        Student[] stuArr = {student1, student3};  
        String jsonfromArr =  mapper.writeValueAsString(stuArr);  
        System.out.println(jsonfromArr);   
        Student[] stuArr2 =  mapper.readValue(jsonfromArr, Student[].class);  
        System.out.println(Arrays.toString(stuArr2));  
        
        System.out.println("************************************3");
	}  
}  
