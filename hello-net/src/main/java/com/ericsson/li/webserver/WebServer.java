package com.ericsson.li.webserver;
import java.net.*;
import java.io.*;
// the function of this application is as tomcat
//1. run as java application
//2.ie browser: http://localhost:9999/
public class WebServer {
	public static void main(String[] args) throws Exception {
		while (true) {
		ServerSocket server = new ServerSocket(9999);
		Socket sock = server.accept();
		FileInputStream in = new FileInputStream("e:\\aa.xml");
		OutputStream out = sock.getOutputStream();
		
		int len = 0;
		byte[] buf = new byte[1024];
		while((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
		sock.close();
		server.close();
		System.out.println("done.");

		}
	}
}
