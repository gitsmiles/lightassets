/**
 * 
 */
package com.fost.ssacache.adapter;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;


/**
 * @author Janly
 *
 */
public class XMemcachedClientAdapter extends AbstractClientAdapter {
	private MemcachedClient memcachedClient;
	@Override
	public void setClient(Object obj) {
		if(MemcachedClient.class.isAssignableFrom(obj.getClass())){
			memcachedClient=(MemcachedClient)obj;
		}else{
			throw new java.lang.RuntimeException(XMemcachedClientAdapter.class.getName()+" is wrong!");
		}
	}
	
	
	@Override
	public boolean add(String key, int exp, Object value, int timeout)
			throws TimeoutException, InterruptedException {
		boolean success=false;
		try{
			success=memcachedClient.add(key, exp, value, timeout);
		}catch(Exception e){
			throw new java.lang.RuntimeException(XMemcachedClientAdapter.class.getName()+" add is wrong!");
		}
		return success;
	}
	
	
	@Override
	public void addWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		try{
			memcachedClient.addWithNoReply(key, exp, value);
		}catch(Exception e){
			throw new java.lang.RuntimeException(XMemcachedClientAdapter.class.getName()+" add is wrong!");
		}
	}
	
	
	@Override
	public boolean delete(String key, int timeout) throws TimeoutException,
			InterruptedException {
		
		return super.delete(key, timeout);
	}
	
	
	@Override
	public void deleteWithNoReply(String key) throws InterruptedException {
		
		super.deleteWithNoReply(key);
	}
	
	
	@Override
	public Object get(String key, int timeout) throws TimeoutException,
			InterruptedException {
		
		return super.get(key, timeout);
	}
	
	
	@Override
	public boolean set(String key, int exp, Object value, int timeout)
			throws TimeoutException, InterruptedException {
		
		return super.set(key, exp, value, timeout);
	}
	
	@Override
	public void setWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		
		super.setWithNoReply(key, exp, value);
	}

}
