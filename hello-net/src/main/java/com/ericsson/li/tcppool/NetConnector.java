package com.ericsson.li.tcppool;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetConnector {
	private Socket client;
	private String host;
	private int port;
	
	public NetConnector(String host, int port) throws UnknownHostException, IOException {
		this.host = host;
		this.port = port;
		connect();
	}
	
	public void connect() throws UnknownHostException, IOException {
		client = new Socket(host, port);
		client.setSoTimeout(10000);
/*		try {
			client = new Socket(host, port);
			client.setSoTimeout(10000);
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return false;*/
	}
	
}
