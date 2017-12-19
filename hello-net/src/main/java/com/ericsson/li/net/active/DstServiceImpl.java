package com.ericsson.li.net.active;

import java.net.Socket;  
/** 
 * @说明 服务的具体执行类 
 * @author 崔素强 
 */  
public class DstServiceImpl implements Runnable {  
    Socket socket = null;  
    public DstServiceImpl(Socket s) {  
        this.socket = s;  
    }  
    public void run() {  
        try {  
            int index = 1;  
            while (true) {  
                // 5秒后中断连接  
                if (index > 10) {  
                    socket.close();  
                    System.out.println("服务端已经将连接关闭！");  
                    break;  
                }  
                index++;  
                Thread.sleep(1 * 1000);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  
