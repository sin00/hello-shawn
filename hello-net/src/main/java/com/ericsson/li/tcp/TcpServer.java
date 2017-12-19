package com.ericsson.li.tcp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
	public static void main(String[] args) throws Exception {
		// 服务端在20006端口监听客户端请求的TCP连接
		ServerSocket server = new ServerSocket(20006);
		Socket client = null;
		boolean f = true;
		while (f) {
			// 等待客户端的连接，如果没有获取连接
			client = server.accept();
			System.out.println("与客户端连接成功！");
			// 为每个客户端连接开启一个线程
			new Thread(new TcpServerThread(client)).start();
		}
		server.close();
	}
	
	private void read() throws IOException {
		/*	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		InputStream input = socket.getInputStream();
		int readLen = 0;
		byte[] buffer = new byte[2048];
		while ((readLen = input.read(buffer)) != -1) {
			bos.write(buffer, 0, readLen);
		}
		
		//toByteArray后会将内存中的数据片段连接返回一个Byte[]  
		//类似StringBuffer
		buffer = bos.toByteArray();
		

		 
int before = input.available();//缓冲区内可读数据
while(true){
    Thread.sleep(100);//try代码省略 sleep时间可以按需要调整
    int ava = input.available();
    //相隔一段时间后 缓冲区内的可读数据没变的话说明客户端已经写入完成 退出循环 一次性将剩余数据取出
    if(ava==before){
        break;
    }
    before = ava;
}

byte[] buffer = new byte[input.available()];
input.read(buffer);
		 */
	}
}
