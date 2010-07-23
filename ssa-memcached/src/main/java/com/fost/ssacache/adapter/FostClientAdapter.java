/**
 * 
 */
package com.fost.ssacache.adapter;
import java.util.concurrent.TimeoutException;

import com.fost.esb.ICacheService;

/**
 * @author Janly
 *
 */
public class FostClientAdapter extends AbstractClientAdapter{
	
	private ICacheService cacheService;
	
	@Override
	public void setClient(Object obj) {
		if(ICacheService.getClass().isAssignableFrom(obj.getClass())){
			cacheService=(ICacheService)cacheService;
		}else{
			throw new java.lang.RuntimeException(FostClientAdapter.class.getName()+" is wrong!");
		}
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
