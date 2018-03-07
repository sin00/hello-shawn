package com.ericsson.li.nio;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class CopyFile {
	static public void main(String args[]) throws Exception {
		/*
		 * if (args.length<2) { System.err.println(
		 * "Usage: java CopyFile infile outfile" ); System.exit( 1 ); }
		 * 
		 * String infile = args[0]; String outfile = args[1];
		 */
		String a = "C:\\workspace\\test\\nio-test\\a.txt";
		String b = "C:\\workspace\\test\\nio-test\\b.txt";
		String c = "C:\\workspace\\test\\nio-test\\c.txt";
		String d = "C:\\workspace\\test\\nio-test\\d.txt";
		String e = "C:\\workspace\\test\\nio-test\\e.txt";

		copy1(a, b);
		copy2(a, c);
		copy3(a, d);
		
		System.out.println("done!");
	}

	public static void copy1(String infile, String outfile) throws Exception {
		FileInputStream fin = new FileInputStream(infile);
		FileOutputStream fout = new FileOutputStream(outfile);

		FileChannel fcin = fin.getChannel();
		FileChannel fcout = fout.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(1024);

		while (true) {
			buffer.clear();

			int r = fcin.read(buffer);

			if (r == -1) {
				break;
			}

			buffer.flip();

			fcout.write(buffer);
		}
		
		fcin.close();
		fin.close();
		fcout.close();
		fout.close();
	}
	
	public static void copy2(String infile, String outfile) throws Exception {
		RandomAccessFile fromFile = new RandomAccessFile(infile, "rw");
		FileChannel fromChannel = fromFile.getChannel();
		RandomAccessFile toFile = new RandomAccessFile(outfile, "rw");
		FileChannel toChannel = toFile.getChannel();
		long position = 0;
		long count = fromChannel.size();
		toChannel.transferFrom(fromChannel, position, count);
		
		fromChannel.close();
		fromFile.close();
		toChannel.close();
		toFile.close();
	}
	
	public static void copy3(String infile, String outfile) throws Exception {
		RandomAccessFile fromFile = new RandomAccessFile(infile, "rw");
		FileChannel fromChannel = fromFile.getChannel();
		RandomAccessFile toFile = new RandomAccessFile(outfile, "rw");
		FileChannel toChannel = toFile.getChannel();
		long position = 0;
		long count = fromChannel.size();
		fromChannel.transferTo(position, count, toChannel);
		fromChannel.close();
		fromFile.close();
		toChannel.close();
		toFile.close();
	}
}
