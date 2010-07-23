package com.fost.ssacache;

import com.fost.ssacache.cluster.EventListener;


/**
 * 
 * @author Janly
 *
 */
public interface CacheFactory {

	public Cache createCache();
	
	public void addListener(EventListener listener);
	

}
