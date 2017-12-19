package com.ericsson.li.net.active;

import java.net.*;  
/** 
 * @说明 服务的客户端，会请求连接并实时打印连接对象的一些信息，但是不会进行流的操作 
 * @author 崔素强 
 */  
public class DstClient1 {  
    public static void main(String[] args) {  
        try {  
            Socket socket = new Socket("127.0.0.1", 8001);  
            socket.setKeepAlive(true);  
            socket.setSoTimeout(10);  
            while (true) {  
                socket.sendUrgentData(0xFF); // 发送心跳包  
                System.out.println("目前是正常的！");  
                Thread.sleep(3 * 1000);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  
