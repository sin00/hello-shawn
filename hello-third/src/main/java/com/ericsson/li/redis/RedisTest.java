package com.ericsson.li.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RedisTest {
	private static Logger logger = LoggerFactory.getLogger(RedisTest.class);

	public static void main(String[] args) {
		 Jedis jedis = new Jedis("192.168.88.128", 6379);
		//Jedis jedis = new Jedis("192.168.88.128");
		//jedis.auth("redis");
		//Jedis jedis = new Jedis("172.21.38.67", 6379);
		//jedis.auth("123456");
		// ping(jedis);
		// testString(jedis);
		// testList(jedis);
		testHash(jedis);
		//testObject(jedis);
		//testByte(jedis);

	}

	public static void ping(Jedis jedis) {
		logger.info(jedis.ping());
	}

	public static void testString(Jedis jedis) {
		jedis.set("runoobkey", "Redis tutorial");
		logger.info("Stored string in redis:: " + jedis.get("runoobkey"));
	}

	public static void testList(Jedis jedis) {
		jedis.lpush("tutorial-list", "Redis");
		jedis.lpush("tutorial-list", "Mongodb");
		jedis.lpush("tutorial-list", "Mysql");
		// 获取存储的数据并输出
		List<String> list = jedis.lrange("tutorial-list", 0, 5);
		for (int i = 0; i < list.size(); i++) {
			logger.info("Stored string in redis:: " + list.get(i));
		}
	}

	public static void testHash(Jedis jedis) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		jedis.hmset("user", map);
		// List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		// logger.info(rsmap.toString());
		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			logger.info(key + ":" + jedis.hmget("user", key));
		}
		
		logger.info("" + jedis.hlen("user")); // 返回key为user的键中存放的值的个数2 
		logger.info("" + jedis.exists("user"));// 是否存在key为user的记录 返回true 
		logger.info("" + jedis.hkeys("user"));// 返回map对象中的所有key 
		logger.info("" + jedis.hvals("user"));// 返回map对象中的所有value 
		
		logger.info("---------------------------");
		
		map.clear();
		map.put("age", "30");
		jedis.hmset("user", map);
		iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			logger.info(key + ":" + jedis.hmget("user", key));
		}
		
		logger.info("" + jedis.hlen("user")); // 返回key为user的键中存放的值的个数2 
		logger.info("" + jedis.exists("user"));// 是否存在key为user的记录 返回true 
		logger.info("" + jedis.hkeys("user"));// 返回map对象中的所有key 
		logger.info("" + jedis.hvals("user"));// 返回map对象中的所有value 

	}
	
	public static void testObject(Jedis jedis) {
		setObject(jedis);
		Person person = getObject(jedis, 100);
		if (null == person) {
			logger.info("person is null.");
			return;
		}
		logger.info("Person[{} - {}]", person.getId(), person.getName());

		person = getObject(jedis, 101);
		logger.info("Person[{} - {}]", new Object[]{person.getId(), person.getName()});
	}

	public static void setObject(Jedis jedis) {
		Person person = new Person(100, "alan");
		jedis.set("litxperson:100".getBytes(), SerializeUtil.serialize(person));
		person = new Person(101, "bruce");
		jedis.set("litxperson:101".getBytes(), SerializeUtil.serialize(person));
	}

	public static Person getObject(Jedis jedis, int id) {
		byte[] person = jedis.get(("litxperson:" + id).getBytes());
		if (person == null) {
			logger.info("jedis.get:person is null.");
			return null;
		}
		
		return (Person) SerializeUtil.unserialize(person);
	}
	
	public static void testByte(Jedis jedis) {
		byte[] b = new byte[] {1,2,3,4,5,4,3,2,1};
		
		for (int i = 0; i < b.length; i++) {
			logger.info("b[" + i + "] = " + b[i]);
		}
		String s = Base64.encodeBase64String(b);
		logger.info("encode to:" + s);
		jedis.set("litx-byte", s);
		s = jedis.get("litx-byte");
		logger.info("get from redis:" + s);
		logger.info("decode to byte:");
		b = Base64.decodeBase64(s);
		for (int i = 0; i < b.length; i++) {
			logger.info("b[" + i + "] = " + b[i]);
		}
		
	}

	public static void readAllKeys(Jedis jedis) {
		Set<String> list = jedis.keys("*");
		for (int i = 0; i < list.size(); i++) {
			// logger.info("List of stored keys:: "+list.);
		}
	}
}
