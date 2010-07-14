package com.fost.ssacache.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SsaNamespaceHandler extends NamespaceHandlerSupport{
	private static final String _spy="ssa-spy";
	private static final String _yan="ssa-yan";
	private static final String _fost="ssa-fost";
	@Override
	public void init() {
		this.registerBeanDefinitionParser(_spy, new SpyBeanDefinitionParser());
		this.registerBeanDefinitionParser(_yan, new YanBeanDefinitionParser());
		this.registerBeanDefinitionParser(_fost, new FostBeanDefinitionParser());
	}
}
