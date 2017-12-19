package com.ericsson.li.tool.vrn;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class VRNApp 
{
	private static Logger logger = LoggerFactory.getLogger(VRNApp.class);
    public static void main( String[] args ) {
		AbstractXmlApplicationContext context = null;
		PlateInfoDao plateInfoDao = null;
		try {
			context = new ClassPathXmlApplicationContext("vrn.xml");
			context.start();
			plateInfoDao = (PlateInfoDao)context.getBean("plateInfoDao");	
			//plateInfoDao.drop();
			//plateInfoDao.create();
			insert(plateInfoDao);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return;
		} finally {
			if (context != null) {
				context.close();
			}
		}
    }
    
    public static void insert(PlateInfoDao plateInfoDao) {
    	String jsonStr = readTextFile("plateinfo");
    	JSONArray ja = JSON.parseObject(jsonStr).getJSONArray("PlateInfo");
		for (int i = 0; i < ja.size(); i++) {
			JSONObject jo = (JSONObject)ja.get(i);
			PlateInfo plateInfo = new PlateInfo();
			plateInfo.setPlatePrefix(jo.getString("code"));
			plateInfo.setCity(jo.getString("city"));
			plateInfo.setProvince(jo.getString("province"));
			logger.info(plateInfo.toString());
			try {
				plateInfoDao.save(plateInfo);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
    	
    }
    
	public static String readTextFile(String filePath) {
		BufferedReader br = null;
		String str = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				str += line;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return str;
	}
}
