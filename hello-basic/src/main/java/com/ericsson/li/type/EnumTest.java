package com.ericsson.li.type;

import java.util.HashMap;
import java.util.Map;

public class EnumTest {
	public enum Color {
		RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);
		// 成员变量
		private String name;
		private int index;

		// 构造方法
		private Color(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 覆盖方法
		@Override
		public String toString() {
			return this.index + "_" + this.name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}	
	}
	
	 public enum Type {
		 REN,GOU,MAO;
	 }

	public enum ValidationKey {
		F195("0", "F195"),
		F193("1", "F193"),
		F187("2", "F187"),
		F18A("3", "F18A"),
		F182("4", "F182");
		private String key;
		private String did;

		private ValidationKey(String key, String did) {
			this.key = key;
			this.did = did;
			//keyDid.put(key, did);
			//didKey.put(did, key);
		}
		public String getKey() {
			return key;
		}

		public String getDid() {
			return did;
		}

		public static String getKey(String did) {return didKey.get(did);}
		public static String getDid(String key) {return keyDid.get(key);}

		@Override
		public String toString() {
			return "ValidationKey{" +
					"key='" + key + '\'' +
					", did='" + did + '\'' +
					'}';
		}

		//private Map<String, String> keyDid = new HashMap<>();
		//private Map<String, String> didKey = new HashMap<>();

		private static Map<String, String> keyDid = new HashMap<>();
		private static Map<String, String> didKey = new HashMap<>();
		private static void init(ValidationKey validationKey) {
			keyDid.put(validationKey.getKey(), validationKey.getDid());
			didKey.put(validationKey.getDid(), validationKey.getKey());
		}

		static {
			init(ValidationKey.F182);
			init(ValidationKey.F187);
			init(ValidationKey.F18A);
			init(ValidationKey.F193);
			init(ValidationKey.F195);
		}

	}

	public static void testColor() {
		System.out.println(Color.RED.toString());
		Color.RED.setIndex(4);
		System.out.println(Color.RED.toString());
		Color cc = Color.RED;
		System.out.println("cc1=" + cc.toString());
		cc.setIndex(5);
		System.out.println("cc2=" + cc.toString());
		System.out.println("cc-r:" + Color.RED.toString());
		System.out.println(Type.GOU);
		System.out.println(Type.MAO);
		String a = Type.MAO.toString();
		System.out.println("a:" + a);
		Type o = Type.valueOf(a);
		System.out.println("o:" + o);
		Type k = Type.valueOf("AAAAA");
		System.out.println("k:" + k);

	}

	public static void testValidationKey() {
		System.out.println(ValidationKey.F18A);
		System.out.println(ValidationKey.F18A.getDid("3"));
		System.out.println(ValidationKey.F18A.getKey("F18A"));

		System.out.println(ValidationKey.F18A.getDid("2"));
		System.out.println(ValidationKey.F18A.getKey("F193"));

		System.out.println(ValidationKey.getDid("2"));
		System.out.println(ValidationKey.getKey("F193"));
	}

	public static void main(String[] args) {
		//testColor();
		testValidationKey();
	}

}
