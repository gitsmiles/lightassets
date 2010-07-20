package com.fost.ssacache.cluster.event;

import com.fost.ssacache.cluster.CacheEvent;
import com.fost.ssacache.cluster.EventManager;


/**
 * @author Janly
 *
 */
public abstract class AbstractCacheEvent implements CacheEvent {

	private EventManager eventManager;
	
	private String key;

	public final String getKey() {
		return key;
	}

	public final void setKey(String key) {
		this.key = key;
	}
	

	@Override
	public EventManager getEventManager() {
		
		return this.eventManager;
	}
	
	@Override
	public void setEventManager(EventManager eventManager) {
		this.eventManager=eventManager;
		
	}
	
	
}
