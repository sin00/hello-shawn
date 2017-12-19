package com.ericsson.li.stream;

import java.io.*;  
import java.net.*;    
public class DstServiceImpl implements Runnable {  
    Socket socket = null;  
    public DstServiceImpl(Socket s) {  
        this.socket = s;  
    }  
    public void run() {  
        try {  
            InputStream ips = socket.getInputStream();  
            OutputStream ops = socket.getOutputStream();  
            while (true) {  
                byte[] bt = StreamTool.readStream(ips);  
                String str = new String(bt);  
                System.out.println("主机收到信息：" + str);  
                String restr = "你好，主机已经收到信息！";  
                ops.write(restr.getBytes());  
                ops.flush();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  