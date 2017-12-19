package com.ericsson.li.tcp;

import java.net.*;

public class MyCloseConnection
{
    public static void printState(Socket socket, String name)
    {
        System.out.println(name + ".isClosed():" + socket.isClosed());
        System.out.println(name + ".isConnected():" + socket.isConnected());
        if (socket.isClosed() == false && socket.isConnected() == true)
            System.out.println(name + "处于连接状态!");
        else
            System.out.println(name + "处于非连接状态!");
        System.out.println();
    }

    public static void main(String[] args) throws Exception
    {
        Socket socket1 = null, socket2 = null;

        socket1 = new Socket("www.ptpress.com.cn", 80);
        printState(socket1, "socket1");
        
        //Thread.sleep(10000);
        
        
        SocketAddress sa = socket1.getRemoteSocketAddress();
        /*
        socket1.close();//close后，下面 获取输入输出流会报错
        printState(socket1, "socket1");*/
        

        socket1.getOutputStream().close();
        printState(socket1, "socket1");
        
        /*
        socket1.getInputStream().close();//关闭输出流后，socket也会close。所以此处获取输入输出流会报错
        printState(socket1, "socket1");*/
        
       
/*        socket1.connect(sa);
        printState(socket1, "socket1-connect");//不行*/
        

        socket2 = new Socket();
        //socket2.connect(sa); //注释掉就不行了
        printState(socket2, "socket2");

        socket2.close();
        printState(socket2, "socket2");
    }
}
