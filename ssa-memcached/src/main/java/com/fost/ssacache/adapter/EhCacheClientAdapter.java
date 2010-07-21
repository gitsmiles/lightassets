/**
 * 
 */
package com.fost.ssacache.adapter;

import java.util.concurrent.TimeoutException;

import com.fost.ssacache.ClientAdapter;

/**
 * @author Janly
 *
 */
public class EhCacheClientAdapter implements ClientAdapter {

	@Override
	public String getGroup() {
		return null;
	}


	@Override
	public boolean isLocal() {
		return false;
	}

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
