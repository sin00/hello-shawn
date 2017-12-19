package com.ericsson.li.ignit.client;

import java.util.concurrent.locks.Lock;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicLong;
import org.apache.ignite.IgniteAtomicReference;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteSet;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.CollectionConfiguration;

public class MyIgnite {
	public  void testIgniteSet() {
		Ignite ignite = Ignition.ignite();
		CollectionConfiguration colCfg = new CollectionConfiguration();

		colCfg.setCollocated(true);
		// Create collocated set.
		IgniteSet<String> set = ignite.set("setName", colCfg);
	}
	
	public  void testIgniteAtomicLong() {
		Ignite ignite = Ignition.ignite();
		// Initialize atomic long.
		final IgniteAtomicLong atomicLong = ignite.atomicLong("atomicName", 0, true);
		// Increment atomic long on local node.
		System.out.println("Incremented value: " + atomicLong.incrementAndGet());
	}
	
	public void testAtomicReference() {
		Ignite ignite = Ignition.ignite();
		// Initialize atomic reference.
		IgniteAtomicReference<String> ref = ignite.atomicReference("refName", "someVal", true);
		// Compare old value to new value and if they are equal,
		//only then set the old value to new value.
		ref.compareAndSet("WRONG EXPECTED VALUE", "someNewVal"); // Won't change.
		
		
		IgniteAtomicReference<Object> refo = ignite.atomicReference("refName", "someVal", true);
		
	}
	
	
	public void testLock() {
		Ignition.start();
		Ignite ignite = Ignition.ignite();

		CacheConfiguration cfg = new CacheConfiguration();

		cfg.setName("myCache");
		cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
		// Create cache with given name, if it does not exist.
		IgniteCache<String, Integer> cache = ignite.getOrCreateCache(cfg);
		
		//cache.withAsync();
		//cache.deregisterCacheEntryListener(arg0);
		Lock lock = cache.lock("Hello");
		lock.lock();
		try {
		    cache.put("Hello", 11);
		    cache.put("World", 22);
		}
		finally {
		    lock.unlock();
		} 
		
	}
	
	public void test0() {
		Ignition.start();
		Ignite ignite = Ignition.ignite();

		CacheConfiguration cfg = new CacheConfiguration();

		cfg.setName("myCache");
		cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
		// Create cache with given name, if it does not exist.
		IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cfg);
		
		//cache.withAsync();
		//cache.deregisterCacheEntryListener(arg0);
		

		int N = 20;
		for (int i = 0; i < N; i++) {			
			cache.put(i, "v" + i);
		}
		
		for (int i = 0; i < N; i++) {			
			System.out.println(i + "=" + cache.get(i));
		}
		
		
		System.out.println(1 + "b=" + cache.get(1));
		cache.replace(1, "v2", "v3");
		System.out.println(1 + "b=" + cache.get(1));
		
		cache.replace(1, "v1", "v3");
		System.out.println(1 + "b=" + cache.get(1));
		

		
	}
}
