package com.fost.ssacache.cluster;

public interface EventListener {

	public boolean captureEvent(CacheEvent cacheEvent);
	
	public void executeEvent(CacheEvent cacheEvent);
}
