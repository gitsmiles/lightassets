/**
 * 
 */
package com.fost.ssacache.impl;

import java.util.concurrent.TimeoutException;

import com.fost.ssacache.Cache;
import com.fost.esb.ICacheService;

/**
 * @author Janly
 *
 */
public class FostCacheServiceCache implements Cache {
	
	private ICacheService cacheService;

	public final ICacheService getCacheService() {
		return cacheService;
	}

	public final void setCacheService(ICacheService cacheService) {
		this.cacheService = cacheService;
	}

	@Override
	public boolean add(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException {
		cacheService.add(key, value, exp);
		return true;
	}

	@Override
	public void addWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		cacheService.add(key, value, exp);
		
	}

	@Override
	public boolean delete(String key, int time) throws TimeoutException,
			InterruptedException {
		cacheService.delete(key, time);
		return true;
	}

	@Override
	public void deleteWithNoReply(String key) throws InterruptedException {
		cacheService.delete(key);
		
	}

	@Override
	public Object get(String key, long timeout) throws TimeoutException,
			InterruptedException {
		return cacheService.get(key);
	}

	@Override
	public boolean set(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException {
		cacheService.update(key, value, exp);
		return true;
	}

	@Override
	public void setWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		
		cacheService.update(key, value, exp);
	}
	
	
	

}
