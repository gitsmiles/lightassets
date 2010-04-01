package com.fost.ssacache.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.fost.ssacache.AnnotationContext;
import com.fost.ssacache.Cache;
import com.fost.ssacache.CacheFactory;

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
