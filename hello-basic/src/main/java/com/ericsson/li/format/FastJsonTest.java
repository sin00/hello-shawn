package com.ericsson.li.format;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject; 
/*
public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray 
public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject    
public static final  T parseObject(String text, Class clazz); // 把JSON文本parse为JavaBean 
public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray 
public static final  List parseArray(String text, Class clazz); //把JSON文本parse成JavaBean集合 
public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本 
public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本 
public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray。
 */

public class FastJsonTest {

	public static void main(String[] args) {
    	//testByte();
    	//testNull();
    	//testIntArry();
		//testJSONObject();
		//testJSONArray();
		testVehicle();
	}

	public static void testJSONObject() {
		String jsonStr = "{\"JACKIE_ZHANG\":\"张学友\",\"ANDY_LAU\":\"刘德华\",\"LIMING\":\"黎明\",\"Aaron_Kwok\":\"郭富城\"}";

		for (int i = 0, j = 5; i < j; i++) {
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
			for (java.util.Map.Entry<String, Object> entry : jsonObject.entrySet()) {
				System.out.print(entry.getKey() + "-" + entry.getValue() + "\t");
			}
			System.out.println();
		}
	}

	public static void testJSONArray(){  
        String jsonStr = "[{\"JACKIE_ZHANG\":\"张学友\"},{\"ANDY_LAU\":\"刘德华\"},{\"LIMING\":\"黎明\"},{\"Aaron_Kwok\":\"郭富城\"}]" ;  
        //做5次测试  
        for(int i=0,j=5;i<j;i++)  
        {  
            JSONArray jsonArray = JSONArray.parseArray(jsonStr);  
  
           for(int k=0;k<jsonArray.size();k++){  
               System.out.print(jsonArray.get(k) + "\t");  
           }  
            System.out.println();//用来换行  
        }  
	}
	   
    public static void testByte() {
    	String str = "{\"data\":[-120,-120,86,25,25,32,32,65]}";
    	JSONObject jo = JSON.parseObject(str);
    	JSONArray ja = jo.getJSONArray("data");
    
        //JSONArray ja = (JSONArray)JSON.parse(jo.get("data").toString());  
        for (Object o : ja) {  
            System.out.println(o.toString());  
        }  
        
        
    }
    
    public static void testIntArry() {
    	String str = "{\"data\":[-120,-120,86,25,25,32,32,65]}";
    	JSONObject jo = JSON.parseObject(str);
    	JSONArray ja = jo.getJSONArray("data");
    	//int[] aa = ((JSONObject)jo.get("data")).parseObject(str, int[].class);
    	//System.out.println(aa); 
        //JSONArray ja = (JSONArray)JSON.parse(jo.get("data").toString());  
        for (int i = 0; i < ja.size(); i++) {  
        	byte b = ((Integer)ja.get(i)).byteValue();
            System.out.println(b);  
        }  
        
        
    }
    
    public static void testNull() {
    	String str = "{\"id\":0,\"name\":\"xiaoming\",\"phoneNo\":[\"18301361567\",\"18301361590\"]}"; 
    	JSONObject jo = JSON.parseObject(str);
    	JSONArray ja = jo.getJSONArray("phoneNo");
    	long id = jo.getLongValue("id");
    	long id1 = jo.getLongValue("id1");
    	System.out.println("id1:" +  id1);
    	System.out.println("id:" +  id);
    
        //JSONArray ja = (JSONArray)JSON.parse(jo.get("data").toString());  
        for (Object o : ja) {  
            System.out.println(o.toString());  
        }  
        
        
    }
	public static void testVehicle() {
		String jsonStr = "{\"status\":\"1\", \"data\":{\"Sequence\":\"12\", \"VIN\":\"4444\", \"SERIES_CODE_VS\":\"R\", \"MODEL_CODE\":123456,\"MAT_CODE\":\"cc\",\"COLOR_CODE\":\"bb\",\"PLANT_ID\":\"8i\",\"Engine_Type\":\"56\"}}";
		//String jsonStr = "{\"status\":\"1\",\"data\":{\"Sequence\":\"12\",\"VIN\":\"4444\",\"SERIES_CODE_VS\":\"R\"}}";
		String jsonStr1 = "{\"JACKIE_ZHANG\":\"张学友\",\"ANDY_LAU\":\"刘德华\",\"LIMING\":\"黎明\",\"Aaron_Kwok\":\"郭富城\"}";
		JSONObject rootObject = JSONObject.parseObject(jsonStr);
		System.out.println("status:" + rootObject.getIntValue("status"));
		JSONObject dataObject = rootObject.getJSONObject("data");
		for (java.util.Map.Entry<String, Object> entry : dataObject.entrySet()) {
			System.out.print(entry.getKey() + "-" + entry.getValue() + "\t");
		}
		System.out.println();
	}
}
