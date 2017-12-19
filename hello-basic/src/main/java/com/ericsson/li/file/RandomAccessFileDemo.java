package com.ericsson.li.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileDemo {
	public static void main(String[] args) throws Exception {
		//normal();
		//download();
		doWriteRead();
	}
	public static void normal() throws Exception {
		RandomAccessFile file = new RandomAccessFile("E:/workspace/test/filemap/randomaccessfile", "rw");
		// 以下向file文件中写数据
		file.writeInt(20);// 占4个字节
		file.writeDouble(8.236598);// 占8个字节
		file.writeUTF("这是一个UTF字符串");// 这个长度写在当前文件指针的前两个字节处，可用readShort()读取
		file.writeBoolean(true);// 占1个字节
		file.writeShort(395);// 占2个字节
		file.writeLong(2325451l);// 占8个字节
		file.writeUTF("又是一个UTF字符串");
		file.writeFloat(35.5f);// 占4个字节
		file.writeChar('a');// 占2个字节

		file.seek(0);// 把文件指针位置设置到文件起始处

		// 以下从file文件中读数据，要注意文件指针的位置
		System.out.println("——————从file文件指定位置读数据——————");
		System.out.println(file.readInt());
		System.out.println(file.readDouble());
		System.out.println(file.readUTF());

		file.skipBytes(3);// 将文件指针跳过3个字节，本例中即跳过了一个boolean值和short值。
		System.out.println(file.readLong());

		file.skipBytes(file.readShort()); // 跳过文件中“又是一个UTF字符串”所占字节，注意readShort()方法会移动文件指针，所以不用加2。
		System.out.println(file.readFloat());

		// 以下演示文件复制操作
		System.out.println("——————文件复制（从file到fileCopy）——————");
		file.seek(0);
		RandomAccessFile fileCopy = new RandomAccessFile("E:/workspace/test/filemap/randomaccessfile.copy", "rw");
		int len = (int) file.length();// 取得文件长度（字节数）
		byte[] b = new byte[len];
		file.readFully(b);
		fileCopy.write(b);
		System.out.println("复制完成！");
	}
	
	public static String byte2String(byte[] b) {
		StringBuilder sb = new StringBuilder();
		int len = b.length-1;
		for (int i = 0; i < len; i++) {
			sb.append(b[i]).append(",");
		}
		sb.append(b[len]);
		return sb.toString();
	}
	
	public static boolean read(String fileName) {
		//List<UCmd> listCmd = new ArrayList<UCmd>();
		RandomAccessFile raf = null;
		byte[] b17 = new byte[17];
		try {
			raf = new RandomAccessFile(fileName, "r");
			int i = 0;
			System.out.println("raf.length:" + raf.length());
			while (raf.getFilePointer() < raf.length()) {
				System.out.println("pointer:" + raf.getFilePointer());
				i++;
				raf.readFully(b17);
				int dataSize = raf.readInt();
				byte[] data = new byte[dataSize];		
				raf.readFully(data);
				//UCmd ucmd = new UCmd(0x03, new String(b17), data);


				System.out.println("data:" + byte2String(data));
				System.out.println(i + " vin:" + byte2String(b17));
				System.out.println(i + " data:" + byte2String(data));
			}	
			

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	} 
	
	public static boolean write(String fileName) {
		//List<UCmd> listCmd = new ArrayList<UCmd>();
		RandomAccessFile raf = null;
		byte[] b17 = new byte[] {0, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4};
		byte[] bb = new byte[] {5, 6, 7, 8, 9};
		try {
            /** 
             * model各个参数详解 
             * r 代表以只读方式打开指定文件 
             * rw 以读写方式打开指定文件 
             * rws 读写方式打开，并对内容或元数据都同步写入底层存储设备 
             * rwd 读写方式打开，对文件内容的更新同步更新至底层存储设备 
             *  
             * **/
			raf = new RandomAccessFile(fileName, "rw");
			raf.seek(raf.length());

			raf.write(b17);
			raf.writeInt(bb.length);
			raf.write(bb);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	} 
	
	public static void doWriteRead() {
		String fileName = "E:/workspace/test/filemap/writeread";
		write(fileName);
		write(fileName);
		read(fileName);
	}
	
	public static void beiju(long skip, String str, String fileName){  
	    try {  
	        RandomAccessFile raf = new RandomAccessFile(fileName,"rw");  
	        if(skip <  0 || skip > raf.length()){  
	            System.out.println("跳过字节数无效");  
	            return;  
	        }  
	        byte[] b = str.getBytes();  
	        raf.setLength(raf.length() + b.length);  
	        for(long i = raf.length() - 1; i > b.length + skip - 1; i--){  
	            raf.seek(i - b.length);  
	            byte temp = raf.readByte();  
	            raf.seek(i);  
	            raf.writeByte(temp);  
	        }  
	        raf.seek(skip);  
	        raf.write(b);  
	        raf.close();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}
	
	 public static void download() throws Exception {  
	        // 预分配文件所占的磁盘空间，磁盘中会创建一个指定大小的文件  
	        RandomAccessFile raf = new RandomAccessFile("E:/workspace/test/filemap/abc.txt", "rw");  
	        raf.setLength(1024*1024); // 预分配 1M 的文件空间  
	        raf.close();  
	          
	        // 所要写入的文件内容  
	        String s1 = "第一个字符串";  
	        String s2 = "第二个字符串";  
	        String s3 = "第三个字符串";  
	        String s4 = "第四个字符串";  
	        String s5 = "第五个字符串";  
	          
	        // 利用多线程同时写入一个文件  
	        new FileWriteThread(1024*1,s1.getBytes()).start(); // 从文件的1024字节之后开始写入数据  
	        new FileWriteThread(1024*2,s2.getBytes()).start(); // 从文件的2048字节之后开始写入数据  
	        new FileWriteThread(1024*3,s3.getBytes()).start(); // 从文件的3072字节之后开始写入数据  
	        new FileWriteThread(1024*4,s4.getBytes()).start(); // 从文件的4096字节之后开始写入数据  
	        new FileWriteThread(1024*5,s5.getBytes()).start(); // 从文件的5120字节之后开始写入数据  
	    }  
	      
	    // 利用线程在文件的指定位置写入指定数据  
	    static class FileWriteThread extends Thread{  
	        private int skip;  
	        private byte[] content;  
	          
	        public FileWriteThread(int skip,byte[] content){  
	            this.skip = skip;  
	            this.content = content;  
	        }  
	          
	        public void run(){  
	            RandomAccessFile raf = null;  
	            try {  
	                raf = new RandomAccessFile("E:/workspace/test/filemap/abc.txt", "rw");  
	                raf.seek(skip);  
	                raf.write(content);  
	            } catch (FileNotFoundException e) {  
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            } finally {  
	                try {  
	                    raf.close();  
	                } catch (Exception e) {  
	                }  
	            }  
	        }  
	    }  
}
