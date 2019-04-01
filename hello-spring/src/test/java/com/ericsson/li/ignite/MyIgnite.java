package com.ericsson.li.ignite;


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
	
	public void test() {
		Ignition.start();
		Ignite ignite = Ignition.ignite();

		CacheConfiguration cfg = new CacheConfiguration();

		cfg.setName("myCache");
		cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);

		// Create cache with given name, if it does not exist.
		IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cfg);
		cache.put(1, "v1");
		System.out.println("aaa" + cache.get(1));
	}
}
