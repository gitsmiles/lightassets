package com.fost.ssacache.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.fost.ssacache.AnnotationContext;
import com.fost.ssacache.Cache;
import com.fost.ssacache.CacheFactory;

public class ClusterCacheFactory implements CacheFactory,org.springframework.context.ApplicationContextAware{

	private ApplicationContext applicationContext;
	@Override
	public Cache createCache(AnnotationContext annotationContext) {
	
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext=arg0;
	}

	
}
