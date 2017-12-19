package com.ericsson.li.mina.time;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaTimeClient {
    private static final int PORT = 6488;
    private static final String IP = "127.0.0.1";
    public static void main(String[] args){
        // 创建客户端连接器.
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", 
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        
        // 设置连接超时检查时间
        connector.setConnectTimeoutCheckInterval(30);
        connector.setHandler(new TimeClientHandler());
        
        // 建立连接
        ConnectFuture cf = connector.connect(new InetSocketAddress(IP, PORT));
        // 等待连接创建完成
        cf.awaitUninterruptibly();
        
        cf.getSession().write("Hi Server!");
        cf.getSession().write("ooooooooooo1");
        cf.getSession().write("kkkkkkkkkkk1");
        cf.getSession().write("ooooooooooo2");
        cf.getSession().write("kkkkkkkkkkk2");
        cf.getSession().write("ooooooooooo3");
        cf.getSession().write("kkkkkkkkkkk3");
        cf.getSession().write("ooooooooooo4");
        cf.getSession().write("kkkkkkkkkkk4");
        cf.getSession().write("ooooooooooo5");
        cf.getSession().write("kkkkkkkkkkk5");
        cf.getSession().write("quit");
        
        // 等待连接断开
        cf.getSession().getCloseFuture().awaitUninterruptibly();
        // 释放连接
        connector.dispose();
    }
}