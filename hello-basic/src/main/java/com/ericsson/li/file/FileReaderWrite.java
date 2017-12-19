package com.ericsson.li.file;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderWrite {

	/**
	 * 将C：\\的myHeart.txt copy 到 D:\\下
	 * 
	 * 首先创建Reader读取数据数据的 读取流对象。
	 * 
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) {
		FileReader fr = null;
		FileWriter fw = null;
		try {
			
			fr = new FileReader("C:\\my.txt");
	        /** 
	         * 创建一个可以往文件中写入字符数据的字符流输出流对象 
	         * 创建时必须明确文件的目的地 
	         * 如果文件不存在，这回自动创建。如果文件存在，则会覆盖。 
	         * 当路径错误时会抛异常 
	         *  
	         * 当在创建时加入true参数，回实现对文件的续写。 
	         */
			fw = new FileWriter("D:\\you.txt");
			// 读一个字符，写一个字符方法
			// int ch = 0;
			//
			// while ((ch = fr.read()) != -1) {
			// fw.write(ch);
			// }
			char[] buf = new char[1024];
			int len = 0;
			// 读一个数组大小，写一个数组大小方法。
			while ((len = fr.read(buf)) != -1) {
				fw.write(buf, 0, len);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (fr != null)
				try {
					fr.close();
				} catch (Exception e2) {
					throw new RuntimeException("关闭失败！");
				}
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
					throw new RuntimeException("关闭失败！");
				}
		}
	}
}
