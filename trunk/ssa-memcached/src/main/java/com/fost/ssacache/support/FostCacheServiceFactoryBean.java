package com.fost.ssacache.support;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;

import com.fost.esb.ICacheService;
import com.fost.esb.cache.CacheManager;

public class FostCacheServiceFactoryBean implements FactoryBean,org.springframework.context.ApplicationContextAware {
	private ApplicationContext applicationContext;
	private String cacheName;
	
	private CacheManager cacheManager;

	@Override
	public Object getObject() throws Exception {
		if(cacheManager==null) {
			String[] beans=BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, CacheManager.class);
			cacheManager=(CacheManager)this.applicationContext.getBean(beans[0], CacheManager.class);
		}
		return cacheManager.getDistributedCache(this.getCacheName());
	}

	@Override
	public Class getObjectType() {
		return ICacheService.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	public final void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public final String getCacheName() {
		return cacheName;
	}

	public final void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}


}
