package com.fost.ssacache.impl;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import com.fost.ssacache.Cache;
import com.fost.ssacache.CacheFactory;
import com.fost.esb.cache.CacheManager;

/**
 * 
 * @author Janly
 *
 */
public class FostCacheServiceCacheFactory implements CacheFactory,org.springframework.context.ApplicationContextAware {
	private ApplicationContext applicationContext;
	private CacheManager cacheManager;
	private String client;
	

	@Override
	public Cache createCache() {
		if(cacheManager==null) {
			String[] beans=BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, CacheManager.class);
			cacheManager=(CacheManager)this.applicationContext.getBean(beans[0], CacheManager.class);
		}
		
		CacheServiceCache cacheServiceCache=new CacheServiceCache();
		cacheServiceCache.setCacheService(cacheManager.getDistributedCache(this.getClient()));
		return cacheServiceCache;
	}

	public final void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public final CacheManager getCacheManager() {
		return cacheManager;
	}

	@org.springframework.beans.factory.annotation.Autowired(required=false)
	public final void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public final String getClient() {
		return client;
	}

	public final void setClient(String client) {
		this.client = client;
	}
	
	

}