package com.fost.ssacache;

public interface EventListener {

	public boolean captureEvent(CacheEvent cacheEvent);
	
	public void executeEvent(CacheEvent cacheEvent);
}
