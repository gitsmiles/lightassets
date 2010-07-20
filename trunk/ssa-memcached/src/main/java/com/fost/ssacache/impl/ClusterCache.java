package com.fost.ssacache.impl;

import java.util.concurrent.TimeoutException;

import com.fost.ssacache.Cache;
import com.fost.ssacache.ClientAdapter;
import com.fost.ssacache.EventManager;
import com.fost.ssacache.event.AddCacheEvent;
/**
 * 
 * @author Janly
 *
 */
public class ClusterCache implements Cache{
	private java.util.List<ClientAdapter> adapters;
	private ClusterEnum mode;
	
	public ClusterCache(String value){
		mode=ClusterEnum.valueOf(value);
	}
	

	
	@Override
	public boolean add(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException {
		switch (mode){
		case sticky:
			return this.getMasterClientAdapter(key).add(key, exp, value, timeout);
		case active:
		case standby:
			AddCacheEvent event=new AddCacheEvent();
			event.setKey(key);
			event.setValue(value);
			event.setTimeout(timeout);
			EventManager.getInstance().publishEvent(event);
			return true;
		}
		
		return false;
	}

	@Override
	public void addWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		switch (mode){
			case sticky:
				this.getMasterClientAdapter(key).addWithNoReply(key, exp, value);
				break;
			case active:
				
				break;
			case standby:
				
				break;
		
		}
		
		
	}

	@Override
	public boolean delete(String key, int time) throws TimeoutException,
			InterruptedException {
		switch (mode){
		case sticky:
			return this.getMasterClientAdapter(key).delete(key, time);
		case active:
			
			break;
		case standby:
			
			break;
	
		}
		return false;
		
	}

	@Override
	public void deleteWithNoReply(String key) throws InterruptedException {
		switch (mode){
		case sticky:
			this.getMasterClientAdapter(key).deleteWithNoReply(key);
			break;
		case active:
			
			break;
		case standby:
			
			break;
	
		}
		
	}

	@Override
	public Object get(String key, long timeout) throws TimeoutException,
			InterruptedException {
		switch (mode){
		case sticky:
			return this.getMasterClientAdapter(key).get(key, timeout);
		case active:
			
			break;
		case standby:
			
			break;
	
		}
		return null;
	}

	@Override
	public boolean set(String key, int exp, Object value, long timeout) throws TimeoutException, InterruptedException {
		
		switch (mode){
		case sticky:
			this.getMasterClientAdapter(key).set(key, exp, value, timeout);
			break;
		case active:
			
			break;
		case standby:
			
			break;
	
		}
		return false;
	}

	@Override
	public void setWithNoReply(String key, int exp, Object value)
			throws InterruptedException {
		
		switch (mode){
		case sticky:
			this.getMasterClientAdapter(key).setWithNoReply(key, exp, value);
			break;
		case active:
			
			break;
		case standby:
			
			break;
	
		}
	}

	private ClientAdapter getMasterClientAdapter(String key){
		int keyCode=key.hashCode();
		int mod=keyCode%adapters.size();
		return adapters.get(mod);
	}
	
}
