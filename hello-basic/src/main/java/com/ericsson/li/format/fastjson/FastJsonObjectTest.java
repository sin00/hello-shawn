package com.ericsson.li.format.fastjson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

public class FastJsonObjectTest {
    public static void main(String[] args) {   
    	//file2json();
    	obj2Json();
    	//certificatedataTest();
    	//testCepVehicle();
    	//testList();
    }  
 
    
    public static void file2json() {
        String userInfo = "{\"id\":0,\"name\":\"xiaoming\",\"phoneNo\":[\"18301361567\",\"18301361590\"]}";  
        printJson(userInfo);
        
        String fileName = "fastjson_userinfo";
        
        userInfo = fileToJson(fileName);
        if (userInfo != null) {
        	printJson(userInfo);
        }
    }
    
    public static void certificatedataTest() {
    	String fileName = "certificatedata.json";
    	String jsonStr = fileToJson(fileName);
    	System.out.println(jsonStr);
    	JSONObject jo = JSON.parseObject(jsonStr);
    	JSONArray ja = jo.getJSONArray("certs");
    	for (int i = 0; i < ja.size(); i++) {
			CertificatedataOrg certificatedataOrg = buildCertificatedataOrg(ja.getJSONObject(i));
			System.out.println(certificatedataOrg.toString());
    	}
    	
    	//JSON
    }
    
	private static CertificatedataOrg buildCertificatedataOrg(JSONObject jo) {
		CertificatedataOrg certificatedataOrg = new CertificatedataOrg();
		certificatedataOrg.setJsonString(jo.toJSONString());
		
		certificatedataOrg.setFingerprint(jo.getString("fingerprint"));
		certificatedataOrg.setSubjectRDN(jo.getString("subjectRDN"));
		certificatedataOrg.setIssuerRDN(jo.getString("issuerRDN"));
		certificatedataOrg.setSerialNumber(jo.getString("serialNumber"));
		certificatedataOrg.setExpireDate(jo.getLongValue("expireDate"));		
		certificatedataOrg.setRevocationDate(jo.getLong("revocationDate"));		
		certificatedataOrg.setStatus(jo.getString("status"));
		
		parseSubjectDN(certificatedataOrg);
		
		return certificatedataOrg;
	}
	
	private static void parseSubjectDN(CertificatedataOrg certificatedataOrg) {	
		HashMap<String, String> hashmap = new HashMap<String, String>();
		String[] strArry = certificatedataOrg.getSubjectRDN().split(",");
		for (String s : strArry) {
			String[] tmpArry = s.trim().split("=");
			if (tmpArry.length != 2) {
				//logger.warn("invalid data:" + s);
				continue;
			}
			
			String key = tmpArry[0].trim();
						
			String value = hashmap.get(tmpArry[0]);
			if (value != null) {
				hashmap.put(key, value + "," + tmpArry[1].trim());
			} else {
				hashmap.put(key, tmpArry[1].trim());
			}
		}
		
		String vin = hashmap.get("CN");
		if (vin.length() == 17) {
			certificatedataOrg.setVin(vin);
		}
		//certificatedataOrg.setEcuType(hashmap.get("UID"));
	}
    
    public static String fileToJson(String fileName) {	
    	String line="";
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
	    	br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
    }
    

    public static void printJson(String userInfo) {  
        // 
        JSONObject jo = JSON.parseObject(userInfo);  
        // 通过正常的方式可以获取指定 键的值  
        System.out.println("id : "  + jo.get("id")); 
        System.out.println("name : "  + jo.get("name"));  
        // 如果要获取 JSONArray 的值，需要先把获取到的 list 数组转换为字符串，然后转换为 Object 再强转为 JSONArray  
        JSONArray ja = (JSONArray)JSON.parse(jo.get("phoneNo").toString());  
        for (Object no : ja) {  
            System.out.println(no.toString());  
        }  
    }
    
	public static void testJSONArray() {
	    //方法１
	    String json = "[{\"companyId\":\"111111111\",\"companyName\":\"Huuuu\",\"_uid\":10,\"_index\":0,\"_state\":\"modified\"},{\"companyId\":\"000000000000000000\",\"companyName\":\"cx01\",\"_uid\":11,\"_index\":1,\"_state\":\"modified\"},{\"companyId\":\"9999999999999\",\"companyName\":\"ttt\",\"_uid\":12,\"_index\":2,\"_state\":\"modified\"}]";
	    List<HashMap> list =JSON.parseArray(json, HashMap.class);
	    for(int i=0;i<list.size();i++){
	      System.out.println(list.get(i).get("companyId"));;
	    }
	    //方法２
	    /*
	    JSONArray jarr = JSONArray.parseArray(json);
	    for (Iterator iterator = jarr.iterator(); iterator.hasNext();) {
	      JSONObject job = (JSONObject) iterator.next();
	      System.out.println(job.get("companyId").toString());
	    }*/
	}

	
	public static void obj2Json() {
		
		CepVehicle cepVehicle = new CepVehicle();
		cepVehicle.setId(1);
		cepVehicle.setVin("vin123");
		cepVehicle.setModel_code("modle123");
		cepVehicle.setSeries_code_vs("serial123");
		
		Group group = new Group();
		group.setCepVehicle(cepVehicle);
		group.setId(0L);
		group.setName("admin");

		User2 guestUser2 = new User2();
		guestUser2.setId(2L);
		guestUser2.setName("guest");

		User2 rootUser2 = new User2();
		rootUser2.setId(3L);
		rootUser2.setName("root");

		group.getUser2s().add(guestUser2);
		group.getUser2s().add(rootUser2);

		String jsonString = JSON.toJSONString(group);

		System.out.println(jsonString);
		
		Group group2 = JSON.parseObject(jsonString, Group.class);
		System.out.println("==============");
		System.out.println(JSON.toJSONString(group2));
	}
	
	public static void testCepVehicle() {
		CepVehicle cepVehicle = new CepVehicle();
		cepVehicle.setId(1);
		cepVehicle.setVin("vin123");
		cepVehicle.setModel_code("modle123");
		cepVehicle.setSeries_code_vs("serial123");
		System.out.println("testCepVehicle>>>>>> " + JSON.toJSONString(cepVehicle));
		
		String jsonStr = "[{\"id\":23,\"VIN\":\"v123\",\"SERIES_CODE_VS\":\"s123\",\"MODEL_CODE\":\"m123\"},{\"id\":33,\"VIN\":\"v456\",\"SERIES_CODE_VS\":\"s456\",\"MODEL_CODE\":\"m456\"}]";
		List<CepVehicle> listCepVehicle = JSONObject.parseArray(jsonStr, CepVehicle.class);
		for (CepVehicle cv : listCepVehicle) {
			System.out.println(cv.toString());
		}	
	}
	
	public static void testList1() {
		CepVehicle cepVehicle = new CepVehicle();
		cepVehicle.setId(1);
		cepVehicle.setVin("vin123");
		cepVehicle.setModel_code("modle123");
		cepVehicle.setSeries_code_vs("serial123");
		List<Object> listCepVehicle = new ArrayList<Object>();
		
		CepVehicle cepVehicle2 = new CepVehicle();
		cepVehicle2.setId(2);
		cepVehicle2.setVin("vin123");
		cepVehicle2.setModel_code("modle123");
		cepVehicle2.setSeries_code_vs("serial123");
		
		listCepVehicle.add(cepVehicle);
		listCepVehicle.add(cepVehicle2);
		System.out.println(listCepVehicle);
		
		String str = JSON.toJSONString(listCepVehicle);
		System.out.println(str);
	}
	
	public static void testList() {
		CepVehicle cepVehicle = new CepVehicle();
		cepVehicle.setId(1);
		cepVehicle.setVin("vin123");
		cepVehicle.setModel_code("modle123");
		cepVehicle.setSeries_code_vs("serial123");
		List<Object> listCepVehicle = new ArrayList<Object>();
		
		CepVehicle cepVehicle2 = new CepVehicle();
		cepVehicle2.setId(2);
		cepVehicle2.setVin("vin123");
		cepVehicle2.setModel_code("modle123");
		cepVehicle2.setSeries_code_vs("serial123");
		
		listCepVehicle.add(cepVehicle);
		listCepVehicle.add(cepVehicle2);
		System.out.println(listCepVehicle);
		
		String str = JSON.toJSONString(listCepVehicle);
		System.out.println(str);
	}
}


class Person1 {
	private String sex;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}

class User2 extends Person1 {
	private Long id;
	private String name;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
class Group {
	private Long id;
	private String name;
	private List<Person1> users = new ArrayList<Person1>();
	private CepVehicle cepVehicle;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public List<Person1> getUser2s() { return users; }
	public void setUser2s(List<Person1> users) { this.users = users; }
	public CepVehicle getCepVehicle() {
		return cepVehicle;
	}
	public void setCepVehicle(CepVehicle cepVehicle) {
		this.cepVehicle = cepVehicle;
	}
}

//class CepVehicle {}

