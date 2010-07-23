package com.fost.ssacache.impl;

import com.fost.ssacache.Cache;
import com.fost.ssacache.CacheFactory;
import com.fost.ssacache.ClientAdapter;
import com.fost.ssacache.cluster.EventListener;
import com.fost.ssacache.cluster.EventManager;

public class ClusterCacheFactory implements CacheFactory{
	private String mode;
	private String name;
	private java.util.List<ClientAdapter> clients;
	
	
	@Override
	public Cache createCache(){
		ClusterCache clusterCache=new ClusterCache(this.getMode());
		for(ClientAdapter client:clients){
			clusterCache.addClientAdapter(client);
		}
		return clusterCache;
	}

	

	@Override
	public void addListener(EventListener listener) {
		EventManager.getInstance().addListener(listener);
	}



	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	

	public final String getName() {
		return name;
	}


	public final void setName(String name) {
		this.name = name;
	}


	public final java.util.List<ClientAdapter> getClients() {
		return clients;
	}

	public final void setClients(java.util.List<ClientAdapter> clients) {
		this.clients = clients;
	}
	
}
