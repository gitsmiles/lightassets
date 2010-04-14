package com.fost.ssacache.config;

import java.util.List;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class YanBeanDefinitionParser extends BaseBeanDefinitionParser{

	@Override
	protected List<PropertyValue> parseSsaContextBeanPropertyDefinition(Element element, ParserContext parserContext) {
		List<PropertyValue> pvs=new java.util.ArrayList<PropertyValue>();
		
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.impl.XMemcachedCacheFactory.class);
		
		PropertyValue pv = new PropertyValue("cacheFactory", beanDefinition);
		pvs.add(pv);
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.key.DefaultCacheKeyProvider.class);
		
		
		GenericBeanDefinition temp = new GenericBeanDefinition();
		temp.setBeanClass(com.fost.ssacache.key.DefaultCacheKeyStoreStrategy.class);
		pv = new PropertyValue("cacheKeyStoreStrategy", temp);
		beanDefinition.getPropertyValues().addPropertyValue(pv);
		

		pv = new PropertyValue("cacheKeyProvider", beanDefinition);
		pvs.add(pv);
		
		return pvs;
	}
}
