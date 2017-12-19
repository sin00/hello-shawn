package com.ericsson.li.format.jacson;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;

public class JacksonMain {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// write your code here
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		try {
			// 尝试从JSON中读取对象
			User user = mapper.readValue(new File("user.json"), User.class);
			System.out.println(user);
			user.setGender(User.Gender.FEMALE);
			mapper.writeValue(new File("user-modified.json"), user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
