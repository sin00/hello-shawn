package com.ericsson.li.tcppool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class ConnectortPool {
	
		private final int nConnCount = 3;
		//private final Connector[] conns;
		//private final LinkedList<Integer> queue;
		//private final LinkedList<MsgObject> queue;
		//private final LinkedList<MsgObject> queue;
		private final LinkedList<Socket> conns;
		
		private String host = "127.0.0.1";
		private int port = 20006;

	public ConnectortPool() {
		conns = new LinkedList<Socket>();

		try {
			for (int i = 0; i < nConnCount; i++) {
				Socket conn = new Socket(host, port);
				conns.add(conn);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean sendMsgObject(MsgObject msgObj)  {
		Socket conn = getNetConnect();
		
		try {
			PrintStream out = new PrintStream(conn.getOutputStream());
		    BufferedReader buf =  new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	        //获取Socket的输入流，用来接收从服务端发送过来的数据    
 
		releaseNetConnect(conn);
		return false;
	}
	
	private Socket getNetConnect() {
		synchronized (conns) {
			while (conns.isEmpty()) {
				try {
					conns.wait();
				} catch (InterruptedException ignored) {
				}
			}
			return conns.removeFirst();
		}
	}
	
	private void releaseNetConnect(Socket conn) {
		synchronized (conns) {
			conns.addLast(conn);
			conns.notify();
		}
	}
	
	/*private class Connector {
		private Socket client;
		//private int soOutTime;
		private String host;
		private int port;
		public Connector(String host, int port) throws UnknownHostException, IOException {
			this.host = host;
			this.port = port;
			connect();
		}
		
		public void connect() throws UnknownHostException, IOException {
			client = new Socket(host, port);
			client.setSoTimeout(10000);
		}
		
		public void sentMsgObject(MsgObject msgObj) {
			//获取Socket的输出流，用来发送数据到服务端    
	        PrintStream out = new PrintStream(client.getOutputStream());  
	        //获取Socket的输入流，用来接收从服务端发送过来的数据    
	        BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));  
		}
		
		public void run() {
			MsgObject msgObj;
			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
						}
					}
					msgObj = queue.removeFirst();
				}
				try {
					r.run();
				} catch (RuntimeException e) {
				}
			}
		}
	}*/
	

	
/*
		// 提交工作任务，实际将任务放入队列，并通知线程进行消费
		public void execute(MsgObject msgObj) {
			synchronized (queue) {
				queue.addLast(msgObj);
				queue.notify();
			}
		}

		private class ConnWorker {
			private Socket client;
			public void run() {
				Runnable r;
				// 循环取出任务队列里的任务进行消费，如果没有任务，就等待任务到来。
				while (true) {
					synchronized (queue) {
						while (queue.isEmpty()) {
							try {
								queue.wait();
							} catch (InterruptedException ignored) {
							}
						}
						///r = queue.removeFirst();
					}
					try {
						//r.run();
					} catch (RuntimeException e) {
					}
				}
			}
			
			private boolean sendMsgObject(MsgObject msgObj)  {
				return false;
			}
		}*/
}
