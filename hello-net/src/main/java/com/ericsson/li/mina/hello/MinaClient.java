package com.ericsson.li.mina.hello;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {  
    public static void main(String[] args) {  
        MinaClient client = new MinaClient();  
        client.initClient("127.0.0.1", 9898);  
    }  
      
    public void initClient(String host,int port)  
    {  
        NioSocketConnector connector = new NioSocketConnector();  
        MinaClientHandler handler = new MinaClientHandler();  
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));  
        connector.setHandler(handler);  
        ConnectFuture future = connector.connect(new InetSocketAddress(host,port));//创建连接  
        future.awaitUninterruptibly();//阻塞直到连接建立,因为我们后面要使用连接成功之后创建的Session对象来进行写数据的操作  
        IoSession session = future.getSession();//获得Session对象  
        session.write("hello world");  
    }  
}  