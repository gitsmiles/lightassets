package com.fost.ssacache.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import com.fost.ssacache.Cache;
import com.fost.ssacache.CacheFactory;

public class ClusterCacheFactory implements CacheFactory,org.springframework.context.ApplicationContextAware{

	private ApplicationContext applicationContext;
	private String mode;
	private java.util.List<String> clients;
	
	
	@Override
	public Cache createCache(){
		ClusterCache clusterCache=new ClusterCache(this.getMode());
		
		
		
		
		return clusterCache;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		applicationContext=arg0;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
	

	
}
