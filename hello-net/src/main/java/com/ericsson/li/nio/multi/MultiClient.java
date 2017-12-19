package com.ericsson.li.nio.multi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiClient {
	protected static Logger logger = LoggerFactory.getLogger(MultiClient.class);
	private BlockingQueue<String> words;
	private Random random;
	private Selector selector = null;
	private List<SocketChannel> listSocketChannel = new ArrayList<SocketChannel>();
	private static final String UTF_8 = "UTF-8";
	private static CharsetEncoder encoder = Charset.forName(UTF_8).newEncoder();
	private static CharsetDecoder decoder = Charset.forName(UTF_8).newDecoder();

	public static void main(String[] args) throws CharacterCodingException, IOException {	
		logger.info("begin!!!");
		MultiClient multiClient = new MultiClient();
		multiClient.init();
		multiClient.process();
		multiClient.destroy();
		logger.info("done!!!");
	}

	public void init() {
		words = new ArrayBlockingQueue<String>(5);
		try {
			words.put("hi");
			words.put("who");
			words.put("what");
			words.put("where");
			words.put("bye");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		random = new Random();
		try {
			selector = Selector.open();
			for (int port  = 9001; port < 9006; port++) {
				initChannel(port);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initChannel(int port) throws IOException {
		SocketChannel channel = null;
		channel = SocketChannel.open();
		channel.configureBlocking(false);
		// 请求连接
		channel.connect(new InetSocketAddress("localhost", port));
		channel.register(selector, SelectionKey.OP_CONNECT);
		listSocketChannel.add(channel);
	}
	
	public void destroy() {
		for (SocketChannel channel : listSocketChannel) {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
         
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	public void process() throws CharacterCodingException, IOException {
		
		boolean isOver = false;
		while (!isOver) {
			selector.select();
			Iterator ite = selector.selectedKeys().iterator();

			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				ite.remove();
				SocketChannel channel = (SocketChannel) key.channel();
				logger.info("!!!!!!!!!!" + channel.getRemoteAddress().toString());
				if (key.isConnectable()) {
					if (channel.isConnectionPending()) {
						if (channel.finishConnect()) {
							// 只有当连接成功后才能注册OP_READ事件
							key.interestOps(SelectionKey.OP_READ);

							channel.write(
									encoder.encode(CharBuffer.wrap(channel.getRemoteAddress().toString() + getWord())));
							sleep();
						} else {
							key.cancel();
						}
					}
				} else if (key.isReadable()) {
					ByteBuffer byteBuffer = ByteBuffer.allocate(128);
					channel.read(byteBuffer);
					byteBuffer.flip();
					CharBuffer charBuffer = decoder.decode(byteBuffer);
					String answer = charBuffer.toString();
					System.out.println(Thread.currentThread().getId() + "-" + channel.getRemoteAddress().toString()  + "--" + answer);

					String word = getWord();
					if (word != null) {
						channel.write(encoder.encode(CharBuffer.wrap(word)));
					} else {
						isOver = true;
					}
					sleep();
				}
			}

		}
	}

	private String getWord() {
		return words.poll();
	}

	private void sleep() {
		try {
			TimeUnit.SECONDS.sleep(random.nextInt(3));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sleep(long l) {
		try {
			TimeUnit.SECONDS.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
