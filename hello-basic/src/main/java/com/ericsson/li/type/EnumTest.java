package com.ericsson.li.type;

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

	public static void main(String[] args) {
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

}
