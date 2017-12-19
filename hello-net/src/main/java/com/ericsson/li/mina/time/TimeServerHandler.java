package com.ericsson.li.mina.time;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 服务器端业务逻辑
 */
public class TimeServerHandler extends IoHandlerAdapter {

    /**
     * 连接创建事件
     */
    @Override
    public void sessionCreated(IoSession session){
        // 显示客户端的ip和端口
        System.out.println(session.getRemoteAddress().toString());
    }
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
    
    /**
     * 消息接收事件
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String strMsg = message.toString();
        if (strMsg.trim().equalsIgnoreCase("quit")) {
        	//Thread.sleep(20000);
           // session.close(true);
            return;
        }
        //Thread.sleep(1000);
        // 返回消息字符串
        session.write("Hi Client! back=>"  + strMsg);
        
        // 打印客户端传来的消息内容
        System.out.println("THREADID:" + Thread.currentThread().getId() + "  =>  Message receive : " + strMsg);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("IDLE" + session.getIdleCount(status));
    }
    
    @Override
    public void sessionClosed(IoSession session) throws Exception {
    	System.out.println("Close:" + session.getRemoteAddress().toString());
    }
    
}
