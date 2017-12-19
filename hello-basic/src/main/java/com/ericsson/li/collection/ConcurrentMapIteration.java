package com.ericsson.li.collection;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentMapIteration {
	private final Map<String, String> map = new ConcurrentHashMap<String, String>();

	private final static int MAP_SIZE = 30;

	public static void main(String[] args) {
		new ConcurrentMapIteration().run();
	}

	public ConcurrentMapIteration() {
		for (int i = 0; i < MAP_SIZE; i++) {
			map.put("key" + i, UUID.randomUUID().toString());
		}
	}

	private final ExecutorService executor = Executors.newCachedThreadPool();

	private final class Accessor implements Runnable {
		private final Map<String, String> map;

		public Accessor(Map<String, String> map) {
			this.map = map;
		}

		public void run() {
			for (Map.Entry<String, String> entry : this.map.entrySet()) {
				try{
					Thread.currentThread().sleep(1000);
				} catch (Exception e) {
					
				}
				System.out.println(
						Thread.currentThread().getName() + " - [" + entry.getKey() + ", " + entry.getValue() + ']');
			}
		}
	}

	private final class Mutator implements Runnable {

		private final Map<String, String> map;
		private final Random random = new Random();

		public Mutator(Map<String, String> map) {
			this.map = map;
		}

		public void run() {
/*			for (int i = 0; i < MAP_SIZE; i++) {
				this.map.remove("key" + random.nextInt(MAP_SIZE));
				//this.map.put("key" + random.nextInt(MAP_SIZE), UUID.randomUUID().toString());
				try{
					Thread.currentThread().sleep(500);
				} catch (Exception e) {
					
				}
				System.out.println(Thread.currentThread().getName() + ": " + i);
			}*/
			Iterator<Map.Entry<String, String>> it = this.map.entrySet().iterator();
			int i = 0;
			while (it.hasNext()) {
				try{
					Thread.currentThread().sleep(500);
				} catch (Exception e) {
					
				}
				Map.Entry<String, String> entry = it.next();
				System.out.println(Thread.currentThread().getName() + ": " + entry.getKey() + ":" + (i++));
				it.remove();
			}
		}
	}

	private void run() {
		Accessor a1 = new Accessor(this.map);
		Accessor a2 = new Accessor(this.map);
		Mutator m = new Mutator(this.map);

		executor.execute(a1);
		executor.execute(m);
		//executor.execute(a2);
	}
}
