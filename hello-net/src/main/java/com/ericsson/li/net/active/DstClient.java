package com.ericsson.li.net.active;

import java.net.*;  
/** 
 * @说明 服务的客户端，会请求连接并实时打印连接对象的一些信息，但是不会进行流的操作 
 * @author 崔素强 
 */  
public class DstClient {  
    public static void main(String[] args) {  
        try {  
            Socket socket = new Socket("127.0.0.1", 8001);  
            socket.setKeepAlive(true);  
            //socket.setSoTimeout(10);  
            
            while (true) {  
                System.out.println(socket.isBound());  
                System.out.println(socket.isClosed());  
                System.out.println(socket.isConnected());  
                System.out.println(socket.isInputShutdown());  
                System.out.println(socket.isOutputShutdown());  
               // Thread.sleep(10000);  
                System.out.println("------------------------0");
                socket.setSoTimeout(10);  
                socket.getInputStream().read();
                System.out.println("------------------------1"); 
                new Thread(new Runnable() {  
                    @Override  
                    public void run() {  
                    	try {
                    		System.out.println(Thread.currentThread().getName() + "------------------------"); 
                            Thread.sleep(5000); 
							socket.setSoTimeout(5000); //当某线程在无超时阻塞读时，其它线程设置timeout并不能让该线程超时
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }  
                }).start();
                System.out.println(Thread.currentThread().getName() + "------------------------"); 
                socket.setSoTimeout(0);
                int r = socket.getInputStream().read();

                System.out.println("------------------------2:" + r); 
                //socket.getOutputStream().write(0xFF);
               // Thread.sleep(3 * 1000);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
