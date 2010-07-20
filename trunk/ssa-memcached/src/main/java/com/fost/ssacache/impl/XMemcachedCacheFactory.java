package com.fost.ssacache.impl;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import com.fost.ssacache.Cache;
import com.fost.ssacache.CacheFactory;

public class XMemcachedCacheFactory implements CacheFactory,org.springframework.context.ApplicationContextAware{
	private ApplicationContext applicationContext;
	
	@Override
	public Cache createCache() {
		XMemcachedCache xMemcachedCache=new XMemcachedCache();
		xMemcachedCache.setMemcachedClient((MemcachedClient)this.applicationContext.getBean(annotationContext.getCacheName(), MemcachedClient.class));
		return xMemcachedCache;
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	
}
