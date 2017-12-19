package com.ericsson.li.format;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;




import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.JSONSerializer;


/**
 * 使用json-lib构�?和解析Json数据
 * 
 * @author litx
 * @date 2015/01/28
 *
 */



public class SFJsonTest {

    /**
     * 构�?Json数据
     * 
     * @return
     */
	
	public static class IdxInfo extends Object {
		private String idxID;
		private	String idxPeriod;
		private Map<String, String> aggRuleJson;// = new HashMap<String, String>();	
		private int idxDimCnt;
		private int idxValueCnt;
		private Map<String, String[]> aggRule;// = new HashMap<String, String[]>();
		
		private String tttttt;
		public String toJosn() {
		/*
		 * 	private String idxID;
		private	String idxPeriod;
		private HashMap<String, String> aggRuleJson = new HashMap<String, String>();	
		private int idxDimCnt;
		private int idxValueCnt;
		private HashMap<String, String[]> aggRule = new HashMap<String, String[]>();
		 * */
			aggRuleJson = new HashMap<String, String>();
			// JSON格式数据解析对象
			idxID = "CM-01-0001-0001";
			idxPeriod = "20150101";
			idxDimCnt = 2;
			idxValueCnt = 3;
			String str = "vc_city,1,2";
			aggRuleJson.put("CM-01-0001-0001_01", str);
			//aggRule.put("CM-01-0001-0001_1", str.split(","));
			str = "vc_city";
			aggRuleJson.put("CM-01-0001-0001_02", str);
			//aggRule.put("CM-01-0001-0001_2", str.split(","));
			str = "1";
			aggRuleJson.put("CM-01-0001-0001_03", str);
			//aggRule.put("CM-01-0001-0001_3", str.split(","));
			str = "2";
			aggRuleJson.put("CM-01-0001-0001_04", str);
			//aggRule.put("CM-01-0001-0001_4", str.split(","));
			str = "vc_city,1";
			aggRuleJson.put("CM-01-0001-0001_05", str);
			//aggRule.put("CM-01-0001-0001_5", str.split(","));
			str = "vc_city,2";
			aggRuleJson.put("CM-01-0001-0001_06", str);
			//aggRule.put("CM-01-0001-0001_6", str.split(","));
			str = "1,2";
			aggRuleJson.put("CM-01-0001-0001_07", str);
			//aggRule.put("CM-01-0001-0001_7", str.split(","));
			
		
			
	        JSONObject jo = new JSONObject();
	        jo.put("idxinfo", JSONArray.fromObject(this));
	        System.out.println(jo.toString());

	        return jo.toString();
			
		}
		public void fromJosn(String jsonString) {
	        // 以employee为例解析，map类似
	        JSONObject jb = JSONObject.fromObject(jsonString);
	        JSONArray ja = jb.getJSONArray("idxinfo");
	    	/*
			 * 	private String idxID;
			private	String idxPeriod;
			private HashMap<String, String> aggRuleJson = new HashMap<String, String>();	
			private int idxDimCnt;
			private int idxValueCnt;
			private HashMap<String, String[]> aggRule = new HashMap<String, String[]>();
			 * */
	        
	        idxID = ja.getJSONObject(0).getString("idxID");
	        idxPeriod = ja.getJSONObject(0).getString("idxPeriod");
	        //aggRuleJson = ja.getJSONObject(0).getJSONObject("aggRuleJson");
	        //aggRule = ja.getJSONObject(0).getJSONObject("aggRule");
	        //setAggRule(ja.getJSONObject(0).getJSONObject("aggRule"));
	        idxDimCnt = ja.getJSONObject(0).getInt("idxDimCnt");
	        idxValueCnt = ja.getJSONObject(0).getInt("idxValueCnt");
	       
	        System.out.println("=============");
	        //aggRuleJson = new HashMap<String, String>();
    		JSONObject aggRuleJsonObj = ja.getJSONObject(0).getJSONObject("aggRuleJson");
    		Iterator<?> keys = aggRuleJsonObj.keys();
    		while(keys.hasNext()){
    			String key = (String) keys.next();
    			String value=aggRuleJsonObj.get(key).toString();
    			System.out.println(key + ":" + value);
    		}
    			
    		/*	
    		Map<String, String> tmp = ja.getJSONObject(0).getJSONObject("aggRuleJson");
	        Iterator it = tmp.entrySet().iterator();
	        while(it.hasNext()){
				Map.Entry entry = (Map.Entry) it.next();
				// entry.getKey();
				// entry.getValue();
				System.out.println(entry.getKey() + ":" + entry.getValue());
				System.out.println(entry.getKey().toString());
				System.out.println(entry.getValue().toString());
			}
	        */
	  
		}
		public String getIdxID() {
			return idxID;
		}
		public void setIdxID(String idxID) {
			this.idxID = idxID;
		}
		public String getIdxPeriod() {
			return idxPeriod;
		}
		public void setIdxPeriod(String idxPeriod) {
			this.idxPeriod = idxPeriod;
		}
		
		public int getIdxDimCnt() {
			return idxDimCnt;
		}

		public void setIdxDimCnt(int idxDimCnt) {
			this.idxDimCnt = idxDimCnt;
		}

		public int getIdxValueCnt() {
			return idxValueCnt;
		}

		public void setIdxValueCnt(int idxValueCnt) {
			this.idxValueCnt = idxValueCnt;
		}
		public Map<String, String> getAggRuleJson() {
			return aggRuleJson;
		}
		public void setAggRuleJson(Map<String, String> aggRuleJson) {
			this.aggRuleJson = aggRuleJson;
		}
		public Map<String, String[]> getAggRule() {
			return aggRule;
		}
		/*
		public void setAggRule(Map<String, String[]> aggRule) {
			this.aggRule = aggRule;
		}*/
	}
	
	public static class Employee extends Object {
		private String name;
		private String sex;
		private int age;
		private Map<String, String> course = new HashMap<String, String>();
		public void initCourse() {
			course.put("Math", "100");
			course.put("English", "90");
			course.put("Physic", "80");
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public Map<String, String> getCourse() {
			return course;
		}
		public void setCourse(Map<String, String> course) {
			this.course = course;
		}
	}
	
    public static String BuildJson() {

        // JSON格式数据解析对象
        JSONObject jo = new JSONObject();

        // 下面构�?两个map、一个list和一个Employee对象
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("name", "Alexia");
        map1.put("sex", "female");
        map1.put("age", "23");

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("name", "Edward");
        map2.put("sex", "male");
        map2.put("age", "24");

        List<Map> list = new ArrayList<Map>();
        list.add(map1);
        list.add(map2);

        Employee employee = new Employee();
        employee.setName("wjl");
        employee.setSex("female");
        employee.setAge(24);
        employee.initCourse();

        // 将Map转换为JSONArray数据
        JSONArray ja1 = JSONArray.fromObject(map1);
        // 将List转换为JSONArray数据
        JSONArray ja2 = JSONArray.fromObject(list);
        // 将Bean转换为JSONArray数据
        JSONArray ja3 = JSONArray.fromObject(employee);

        System.out.println("JSONArray对象数据格式");
        System.out.println(ja1.toString());
        System.out.println(ja2.toString());
        System.out.println(ja3.toString());

        // 构�?Json数据，包括一个map和一个Employee对象
        //jo.put("map", ja1);
        //jo.put("employee", ja2);
        jo.put("employee", ja3);
        System.out.println("======");
        System.out.println(jo.toString());

        return jo.toString();

    }

    /**
     * 解析Json数据
     * 
     * @param jsonString Json数据字符�?
     */
    public static void ParseJson(String jsonString) {

        // 以employee为例解析，map类似
        JSONObject jb = JSONObject.fromObject(jsonString);
        JSONArray ja = jb.getJSONArray("employee");

        List<Employee> empList = new ArrayList<Employee>();

        // 循环添加Employee对象（可能有多个�?
        for (int i = 0; i < ja.size(); i++) {
            Employee employee = new Employee();

            employee.setName(ja.getJSONObject(i).getString("name"));
            employee.setSex(ja.getJSONObject(i).getString("sex"));
            employee.setAge(ja.getJSONObject(i).getInt("age"));

            empList.add(employee);
        }

        System.out.println("\n将Json数据转换为Employee对象");
        for (int i = 0; i < empList.size(); i++) {
            Employee emp = empList.get(i);
            System.out.println("name: " + emp.getName() + " sex: "
                    + emp.getSex() + " age: " + emp.getAge());
        }

    }

    public static void ParseJson2(String jsonString) {

        // 以employee为例解析，map类似
        JSONObject jb = JSONObject.fromObject(jsonString);
        JSONArray ja = jb.getJSONArray("employee");

        List<Employee> empList = new ArrayList<Employee>();

        // 循环添加Employee对象（可能有多个�?
        for (int i = 0; i < ja.size(); i++) {
            Employee employee = new Employee();

            employee.setName(ja.getJSONObject(i).getString("name"));
            employee.setSex(ja.getJSONObject(i).getString("sex"));
            employee.setAge(ja.getJSONObject(i).getInt("age"));
            employee.setCourse(ja.getJSONObject(i).getJSONObject("coursd"));
            employee.setCourse(ja.getJSONObject(i).getJSONObject("course"));
            

            empList.add(employee);
        }

        System.out.println("\n将Json数据转换为Employee对象");
        for (int i = 0; i < empList.size(); i++) {
            Employee emp = empList.get(i);
            System.out.println("name: " + emp.getName() + " sex: "
                    + emp.getSex() + " age: " + emp.getAge());
            
            Iterator it = emp.getCourse().entrySet().iterator();
            while(it.hasNext()){
            Map.Entry entry = (Map.Entry)it.next();
           //entry.getKey();
           //entry.getValue();
            System.out.println(entry.getKey() + ":" + entry.getValue());
            } 
        }

    }
    
    public static void excludeJson() {
    	String str = "{'string': 'JSON', 'integer': 1, 'double': 2.0, 'boolean': true}";
    	JsonConfig jsonConfig =  new JsonConfig();
    	jsonConfig.setExcludes(new String[]{"double", "boolean"});
    	JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(str, jsonConfig);
    	System.out.println(jsonObject.toString());
    	System.out.println(jsonObject.getString("string"));
    	System.out.println(jsonObject.getString("integer"));
    	System.out.println(jsonObject.has("double"));
    	//System.out.println(jsonObject.getString("double"));
    	
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //ParseJson(BuildJson());
    	//ParseJson2(BuildJson());
    	
    	
    	
    	JSONObject jo = new JSONObject();
     	IdxInfo idx = new IdxInfo();
    	String jsonString = idx.toJosn(); 	
    	IdxInfo idx2 = new IdxInfo();
    	idx2.fromJosn(jsonString);
    	
    	
    	//excludeJson();
    	
    	/*
    	String strArgTest[] = {"hello", "world", "1", "2", "3"};
    	Arrays.sort(strArgTest);
    	
    	System.out.println(Arrays.binarySearch(strArgTest, "2"));*/
    	
    }

}
