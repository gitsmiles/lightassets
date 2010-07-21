/**
 * 
 */
package com.fost.ssacache.impl;

import java.util.concurrent.TimeoutException;

import com.fost.ssacache.Cache;

/**
 * @author Janly
 *
 */
public class SpyMemcachedCache implements Cache{

	@Override
	public boolean add(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException {
		
		return false;
	}

	@Override
	public void addWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		
		
	}

	@Override
	public boolean delete(String key, long timeout) throws TimeoutException,
			InterruptedException {
		
		return false;
	}

	@Override
	public void deleteWithNoReply(String key) throws InterruptedException {
		
		
	}

	@Override
	public Object get(String key, long timeout) throws TimeoutException,
			InterruptedException {
		
		return null;
	}

	@Override
	public boolean set(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException {
		
		return false;
	}

	@Override
	public void setWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		
		
	}

	
}
