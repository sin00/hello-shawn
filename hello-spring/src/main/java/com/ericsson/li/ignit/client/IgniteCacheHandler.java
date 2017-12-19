package com.ericsson.li.ignit.client;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.cache.CacheException;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteClientDisconnectedException;

/**
 * Ignite cache操作类
 * @author Ryan
 * @since 2016-05-25
 */
public class IgniteCacheHandler {
	
	private long duration;

	private IgniteCache<Object, Object> cache;
	
	public IgniteCacheHandler() {
		
	}
	
	public IgniteCacheHandler(Ignite ignite, String cacheName, long duration) {
		this.duration = duration;
		this.cache = ignite.getOrCreateCache(cacheName);
		cache.withExpiryPolicy(new TouchedExpiryPolicy(
				new Duration(TimeUnit.SECONDS, this.duration)));
	}

	/**
	 * 默认的延时（超时）时长为ignite配置中配置的duration字段值，单位为：秒
	 */
	public void put(Object key, Object value) {
		try {
			cache.put(key, value);
			
		} catch (CacheException  e) {
			reconnect(e);
		}
		
		timeoutTimer(key, duration);
	}
	
	/**
	 * @param delay 指定延时（超时）时长，单位为：秒
	 */
	public void put(Object key, Object value, long delay) {
		try {
			cache.put(key, value);
		} catch (CacheException e) {
			reconnect(e);
		}
		
		timeoutTimer(key, delay);
	}
	
	public Object get(Object key) {
		Object obj = null;
		try {
			obj = cache.get(key);
		} catch (CacheException e) {
			reconnect(e);
		}
		return obj;
	}
	
	public boolean remove(Object key) {
		boolean f = false;
		try {
			f = cache.remove(key);
		} catch (CacheException e) {
			reconnect(e);
		}
		return f;
	}
	
	public Object clear(Object key) {
		Object value = null;
		try {
			value = cache.get(key);
			cache.clear(key);
		} catch (CacheException e) {
			reconnect(e);
		}
		return value;
	}
	
	/**
	 * 默认的延时（超时）时长为ignite配置中配置的duration字段值，单位为：秒
	 */
	public void putAll(Map<Object, Object> kvs) {
		try {
			for (Entry<Object, Object> entry : kvs.entrySet()) {
				put(entry.getKey(), entry.getValue());
			}
		} catch (CacheException e) {
			reconnect(e);
		}
		
	}
	
	/**
	 * @param delay 指定延时（超时）时长，单位为：秒
	 */
	public void putAll(Map<Object, Object> kvs, long delay) {
		try {
			for (Entry<Object, Object> entry : kvs.entrySet()) {
				put(entry.getKey(), entry.getValue(), delay);
			}
		} catch (CacheException e) {
			reconnect(e);
		}
		
	}
	
	public Map<Object, Object> getAll(Set<Object> keys) {
		Map<Object, Object> map = null;
		try {
			map = cache.getAll(keys);
		} catch (CacheException e) {
			reconnect(e);
		}
		return map;
	}
	
	public void removeAll() {
		try {
			cache.removeAll();
		} catch (CacheException e) {
			reconnect(e);
		}
		
	}
	
	public void removeAll(Set<Object> keys) {
		try {
			cache.removeAll(keys);
		} catch (CacheException e) {
			reconnect(e);
		}
		
	}
	
	public void clearAll(Set<Object> keys) {
		try {
			cache.clearAll(keys);
		} catch (CacheException e) {
			reconnect(e);
		}
		
	}
	
	// 设定指定任务task在指定时间delay后执行 schedule(TimerTask task, long delay)  
    private void timeoutTimer(final Object key, long delay) {
        Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {  
                remove(key);
            }  
        }, delay * 1000);// 设定指定的时间time,此处单位：毫秒 
    }
    
    /**
     * 如果客户端与ignite服务端断开连接异常，则触发重连
     */
    private void reconnect(CacheException e) {
    	if (e.getCause() instanceof IgniteClientDisconnectedException) {
		      IgniteClientDisconnectedException cause =
		        (IgniteClientDisconnectedException)e.getCause();
		      cause.reconnectFuture().get(); // Wait for reconnect.
		      // Can proceed and use the same IgniteCache instance.
		    }
    }

}
