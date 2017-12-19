package com.ericsson.li.mina.test;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaServerHandler extends IoHandlerAdapter {  
    protected static Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);  
  
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {  
        logger.error("服务端发送异常...", cause);  
    }  
  
    public void messageReceived(IoSession session, Object message) throws Exception {  
        String msg = message.toString();  
        //如果是quit就关闭session退出    
        if ("quit".equals(msg)) {  
            session.close(true);  
        }  
        Date date = new Date();  
        session.write(date.toString());  
    }  
  
    public void sessionCreated(IoSession session) throws Exception {  
        logger.info("服务端与客户端创建连接...");  
    }  
}  