package com.ericsson.li.net.active;

import java.net.*;  
/** 
 * @说明 从这里启动一个服务端监听某个端口 
 * @author 崔素强 
 */  
public class DstService {  
    public static void main(String[] args) {  
        try {             
            // 启动监听端口 8001  
            ServerSocket ss = new ServerSocket(8001);  
            // 没有连接这个方法就一直堵塞  
            Socket s = ss.accept();  
            // 将请求指定一个线程去执行  
            new Thread(new DstServiceImpl(s)).start();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}