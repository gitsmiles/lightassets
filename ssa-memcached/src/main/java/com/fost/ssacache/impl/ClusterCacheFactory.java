package com.fost.ssacache.impl;

import com.fost.ssacache.Cache;
import com.fost.ssacache.CacheFactory;
import com.fost.ssacache.ClientAdapter;

public class ClusterCacheFactory implements CacheFactory{
	private String mode;
	private java.util.List<ClientAdapter> clients;
	
	
	@Override
	public Cache createCache(){
		ClusterCache clusterCache=new ClusterCache(this.getMode());
		for(ClientAdapter client:clients){
			clusterCache.addClientAdapter(client);
		}
		return clusterCache;
	}


	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public final java.util.List<ClientAdapter> getClients() {
		return clients;
	}

	public final void setClients(java.util.List<ClientAdapter> clients) {
		this.clients = clients;
	}
	
}
