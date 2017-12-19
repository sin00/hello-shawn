package com.ericsson.li.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapFile {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// copy();
		// copy(50, 70);
		appendWrite();
		read(-1, 265);
	}

	private static void copy() {
		try {
			RandomAccessFile rafi = new RandomAccessFile("E:/workspace/test/filemap/aws-java-sdk-1.11.115.zip", "r");
			RandomAccessFile rafo = new RandomAccessFile("E:/workspace/test/filemap/2", "rw");
			FileChannel fci = rafi.getChannel();
			FileChannel fco = rafo.getChannel();
			long size = fci.size();
			MappedByteBuffer mbbi = fci.map(FileChannel.MapMode.READ_ONLY, 0, size);
			MappedByteBuffer mbbo = fco.map(FileChannel.MapMode.READ_WRITE, 0, size);
			long start = System.currentTimeMillis();
			for (int i = 0; i < size; i++) {
				byte b = mbbi.get(i);
				mbbo.put(i, b);
			}
			fci.close();
			fco.close();
			rafi.close();
			rafo.close();
			System.out.println("Spend: " + (double) (System.currentTimeMillis() - start) / 1000 + "s");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void copy(long position, long size) {
		RandomAccessFile rafi = null;
		RandomAccessFile rafo = null;
		FileChannel fci = null;
		FileChannel fco = null;
		try {
			rafi = new RandomAccessFile("E:/workspace/test/filemap/a.txt", "r");
			rafo = new RandomAccessFile("E:/workspace/test/filemap/d.txt", "rw");
			fci = rafi.getChannel();
			fco = rafo.getChannel();
			System.out.println("position:" + position);
			System.out.println("size:" + size);
			System.out.println("src size:" + fci.size());
			if (size < 0 || size > fci.size()) {
				System.out.println("invalid size:" + position);
			}
			if (position < 0 || position >= fci.size()) {
				System.out.println("invalid posiont:" + position);
				return;
			}

			MappedByteBuffer mbbi = fci.map(FileChannel.MapMode.READ_ONLY, position, size);
			MappedByteBuffer mbbo = fco.map(FileChannel.MapMode.READ_WRITE, 0, 2);
			// rafi.close();
			// rafo.close();
			// fci.close();
			// fco.close();
			long start = System.currentTimeMillis();
			System.out.println("read..");
			for (int i = 0; i < size; i++) {
				byte b = mbbi.get(i);
				System.out.print(b + " ");
				mbbo.put(i, b);
			}
			System.out.print("\ndown..");
			System.out.println("Spend: " + (double) (System.currentTimeMillis() - start) / 1000 + "s");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fci != null)
				try {
					fci.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (fco != null)
				try {
					fco.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (rafi != null)
				try {
					rafi.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (rafo != null)
				try {
					rafo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	private static void read(long position, long size) {
		RandomAccessFile rafi = null;

		FileChannel fci = null;

		try {
			rafi = new RandomAccessFile("E:/workspace/test/filemap/a.txt", "r");

			fci = rafi.getChannel();

			/*
			 * System.out.println("position:" + position);
			 * System.out.println("size:" + size); System.out.println(
			 * "src size:" + fci.size()); if (size < 0 || size > fci.size()) {
			 * System.out.println("invalid size:" + position); } if (position <
			 * 0 || position >= fci.size()) { System.out.println(
			 * "invalid posiont:" + position); return; }
			 */
			System.out.println("fci size:" + fci.size());
			System.out.println("fci position:" + fci.position());
/*			if ((position + size) > fci.size()) {
				System.out.println("invalid parm:" + position);
				return;
			}*/

			MappedByteBuffer mbbi = fci.map(FileChannel.MapMode.READ_ONLY, position, size);
			byte[] b = new byte[(int) size];
			mbbi.get(b);
			

			System.out.println("read..");
			for (int i = 0; i < b.length; i++) {
				System.out.print(b[i] + " ");
			}
			System.out.println("down..");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fci != null)
				try {
					fci.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (rafi != null)
				try {
					rafi.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	private static void appendWrite() {
		RandomAccessFile rafo = null;
		FileChannel fco = null;
		try {
			byte[] b = new byte[] { 71, 72, 73, 74 };
			rafo = new RandomAccessFile("E:/workspace/test/filemap/d.txt", "rw");
			fco = rafo.getChannel();
			MappedByteBuffer mbbo = fco.map(FileChannel.MapMode.READ_WRITE, fco.size(), b.length);
			mbbo.put(b);

			System.out.println("down..");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (fco != null)
				try {
					fco.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			if (rafo != null)
				try {
					rafo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	private static void hh() {
		RandomAccessFile rafo = null;
		FileChannel fco = null;
		try {
			byte[] b = new byte[] { 71, 72, 73, 74 };
			rafo = new RandomAccessFile("E:/workspace/test/filemap/d.txt", "rw");
			fco = rafo.getChannel();
			MappedByteBuffer mbbo = fco.map(FileChannel.MapMode.READ_WRITE, fco.size(), b.length);
			mbbo.put(b);

			System.out.println("down..");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (fco != null)
				try {
					fco.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			if (rafo != null)
				try {
					rafo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
