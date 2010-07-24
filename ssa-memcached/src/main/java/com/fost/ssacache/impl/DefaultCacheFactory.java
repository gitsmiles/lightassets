/**
 * 
 */
package com.fost.ssacache.impl;

import com.fost.ssacache.Cache;
import com.fost.ssacache.CacheFactory;
import com.fost.ssacache.ClientAdapter;
import com.fost.ssacache.cluster.EventListener;
import com.fost.ssacache.cluster.EventManager;

/**
 * @author Janly
 *
 */
public class DefaultCacheFactory implements CacheFactory{

	private java.util.List<ClientAdapter> clients;
	@Override
	public Cache createCache() {
		DefaultCache cache=new DefaultCache();
		for(ClientAdapter client:clients){
			cache.addClientAdapter(client);
		}
		return cache;
	}
	
	@Override
	public void addListener(EventListener listener) {
		EventManager.addListener(listener);
		
	}
	
	public final java.util.List<ClientAdapter> getClients() {
		return clients;
	}
	
	
	public final void setClients(java.util.List<ClientAdapter> clients) {
		this.clients = clients;
	}
	
	
	
}
