/**
 * 
 */
package com.fost.ssacache.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import com.fost.ssacache.Cache;
import com.fost.ssacache.CacheFactory;

/**
 * @author Janly
 *
 */
public class SpyMemcachedCacheFactory implements CacheFactory,org.springframework.context.ApplicationContextAware{

	private ApplicationContext applicationContext;
	@Override
	public Cache createCache() {
		return null;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}

	

	
}
