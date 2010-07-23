package com.fost.ssacache.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 
 * @author Janly
 *
 */
public class SsaNamespaceHandler extends NamespaceHandlerSupport{
	private static final String _cluster="ssa-cluster"; 
	private static final String _cache="ssa-cache";
	@Override
	public void init() {
		this.registerBeanDefinitionParser(_cache, new CacheBeanDefinitionParser());
		this.registerBeanDefinitionParser(_cluster, new ClusterBeanDefinitionParser());
	}
}
