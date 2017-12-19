package com.ericsson.li.mina.test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaTest {  
    protected static Logger logger = LoggerFactory.getLogger(MinaTest.class);  
    private static int PORT = 9999;  
  
    public static void main(String[] args) {  
        try {  
            // 创建一个非阻塞的server端的Socket  
            IoAcceptor acceptor = new NioSocketAcceptor();  
            // 设置过滤器  
            acceptor.getFilterChain().addLast("logger", new LoggingFilter());  
            acceptor.getFilterChain().addLast("codec",  
                    new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));  
            acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));  
            // 设置读取数据的缓冲区大小  
            acceptor.getSessionConfig().setReadBufferSize(2048);  
            // 读写通道10秒内无操作进入空闲状态  
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);  
            // 绑定逻辑处理器  
            acceptor.setHandler(new MinaServerHandler());  
            // 绑定端口  
            acceptor.bind(new InetSocketAddress(PORT));  
            logger.info("服务端启动成功... 端口号为：" + PORT);  
        } catch (Exception e) {  
            logger.error("服务端启动异常....", e);  
            e.printStackTrace();  
        }  
    }  
}  
