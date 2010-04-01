package com.fost.ssacache.impl;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

import com.fost.ssacache.AnnotationContext;
import com.fost.ssacache.Cache;
import com.fost.ssacache.CacheFactory;
import com.fost.esb.cache.CacheManager;

/**
 * 
 * @author Janly
 *
 */
public class CacheServiceCacheFactory implements CacheFactory,org.springframework.context.ApplicationContextAware {
	private static final org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(CacheServiceCacheFactory.class);
	private ApplicationContext applicationContext;
	private CacheManager cacheManager;
	private CacheServiceCache cacheServiceCache;

	@Override
	public Cache createCache(AnnotationContext annotationContext) {
		if(cacheServiceCache==null){
			synchronized(this){
				if(cacheServiceCache==null){
					try {
						if(cacheManager==null) {
							String[] beans=BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, CacheManager.class);
							cacheManager=(CacheManager)this.applicationContext.getBean(beans[0], CacheManager.class);
						}
						cacheServiceCache=new CacheServiceCache();
						cacheServiceCache.setCacheService(cacheManager.getDistributedCache(annotationContext.getCacheName()));
						return cacheServiceCache;
					} catch (java.lang.Throwable e) {
						logger.warn(e.getMessage());
					}
				}
			}

		}
		return cacheServiceCache;
	}

	public final ApplicationContext getApplicationContext() {
		return applicationContext;
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

}
