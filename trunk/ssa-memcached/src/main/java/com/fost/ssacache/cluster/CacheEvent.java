package com.fost.ssacache.cluster;


/**
 * @author Janly
 *
 */
public interface CacheEvent {
	
	public EventEnum getEventEnum();
	
	public void setEventManager(EventManager eventManager);
	
	public EventManager getEventManager();
	
}
