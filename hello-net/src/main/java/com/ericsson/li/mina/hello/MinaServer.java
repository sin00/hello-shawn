package com.ericsson.li.mina.hello;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.ericsson.li.mina.test.MinaServerHandler;

public class MinaServer {
	public void init(int port) throws IOException {
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		MinaServerHandler handler = new MinaServerHandler();
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		acceptor.setHandler(handler);
		acceptor.bind(new InetSocketAddress(port));
	}
	
	public static void main(String[] args) throws IOException {
		MinaServer minaServer = new MinaServer();
		minaServer.init(9898);
	}
}
