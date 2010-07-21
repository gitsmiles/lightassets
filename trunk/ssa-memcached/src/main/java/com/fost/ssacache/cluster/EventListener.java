package com.fost.ssacache.cluster;

import java.util.concurrent.TimeoutException;

public interface EventListener {

	public boolean captureEvent(CacheEvent cacheEvent);
	
	public void executeEvent(CacheEvent cacheEvent,boolean syn) throws TimeoutException,InterruptedException;
}
