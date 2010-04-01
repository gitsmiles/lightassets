package com.fost.category.cache.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.fost.category.cache.AnnotationContext;
import com.fost.category.cache.Cache;
import com.fost.category.cache.CacheFactory;

public class XMemcachedCacheFactory implements CacheFactory,org.springframework.context.ApplicationContextAware{

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
	}

	@Override
	public Cache createCache(AnnotationContext annotationContext) {
		return null;
	}

	
}
