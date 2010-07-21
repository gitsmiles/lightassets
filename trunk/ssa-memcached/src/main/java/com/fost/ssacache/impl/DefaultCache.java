/**
 * 
 */
package com.fost.ssacache.impl;

import java.util.concurrent.TimeoutException;

import com.fost.ssacache.Cache;
import com.fost.ssacache.ClientAdapter;

/**
 * @author Janly
 *
 */
public class DefaultCache implements Cache{

	private ClientAdapter clientAdapter;
	
	
	public DefaultCache(ClientAdapter clientAdapter){
		this.clientAdapter=clientAdapter;
	}
	
	@Override
	public boolean add(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException {
		
		return this.clientAdapter.add(key, exp, value, timeout);
	}

	@Override
	public void addWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		
		this.clientAdapter.addWithNoReply(key, exp, value);
	}

	@Override
	public boolean delete(String key, int time) throws TimeoutException,
			InterruptedException {
		
		return this.delete(key, time);
	}

	@Override
	public void deleteWithNoReply(String key) throws InterruptedException {
		
		this.clientAdapter.deleteWithNoReply(key);
	}

	@Override
	public Object get(String key, long timeout) throws TimeoutException,
			InterruptedException {
		
		return this.clientAdapter.get(key, timeout);
	}

	@Override
	public boolean set(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException {
		
		return this.clientAdapter.set(key, exp, value, timeout);
	}

	@Override
	public void setWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		this.clientAdapter.setWithNoReply(key, exp, value);
		
	}

	
}
