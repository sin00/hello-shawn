package com.ericsson.li.mina.time;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TimeClientHandler extends IoHandlerAdapter {

    public void messageReceived(IoSession session, Object message) throws Exception {
        String content = message.toString();
        System.out.println("THREADID:" + Thread.currentThread().getId() + "  =>  client receive a message is : " + content);
    }

    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("THREADID:" + Thread.currentThread().getId() + " => client messageSent -> ：" + message);
    }
    
}