package com.ericsson.li.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.net.Socket;

public class TcpServerThread implements Runnable {
	private Socket client = null;  
    public TcpServerThread(Socket client){  
        this.client = client;  
    } 

    
    public void test() {
    	try{  
            //获取Socket的输出流，用来向客户端发送数据  
            PrintStream out = new PrintStream(client.getOutputStream());  
            //获取Socket的输入流，用来接收从客户端发送过来的数据  
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));  
            boolean flag =true;  
            while(flag){  
                //接收从客户端发送过来的数据  
                String str =  buf.readLine();  
                if(str == null || "".equals(str)){  
                    flag = false;  
                }else{  
                    if("bye".equals(str)){  
                        flag = false;  
                    }else{  
                        //将接收到的字符串前面加上echo，发送到对应的客户端  
                        out.println("echo:" + str);  
                    }  
                }  
            }  
            out.close();  
            client.close();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }
    
    public void recvData() {
		try{  
			
			DataInputStream in;

			in = new DataInputStream(client.getInputStream());
			//定义一个byte数组用来存放读取到的数据，byte数组的长度要足够大。
			byte[] bytes = new byte[100];
			in.read(bytes);//写入byte数组中。再依次读取出来即可
            client.close();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    } 
  
    @Override
	public void run() {
		// TODO Auto-generated method stub
		//recvData();
		test();
  }
}
