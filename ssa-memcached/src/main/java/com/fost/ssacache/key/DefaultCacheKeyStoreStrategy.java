/**
 * 
 */
package com.fost.ssacache.key;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fost.ssacache.CacheKeyStoreStrategy;



/**
 * @author Janly
 *
 */
public class DefaultCacheKeyStoreStrategy implements CacheKeyStoreStrategy {

	final Map<Class<?>, Method> map = new ConcurrentHashMap<Class<?>, Method>();
	@Override
	public void add(Class<?> key, Method value) {
		map.put(key, value);
		
	}

	@Override
	public Method find(Class<?> key) {
		return map.get(key);
	}

	
	
}
