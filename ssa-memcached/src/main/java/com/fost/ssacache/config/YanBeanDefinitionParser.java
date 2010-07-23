package com.fost.ssacache.config;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 
 * @author Janly
 *
 */
public class YanBeanDefinitionParser extends BaseBeanDefinitionParser{

	@Override
	protected MutablePropertyValues parseSsaContextBeanPropertyDefinition(Element element, ParserContext parserContext) {
		MutablePropertyValues pvs=new MutablePropertyValues();
		
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.impl.XMemcachedCacheFactory.class);
		
		pvs.addPropertyValue(new PropertyValue("cacheFactory", beanDefinition));
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.key.DefaultCacheKeyProvider.class);
		
		
		GenericBeanDefinition temp = new GenericBeanDefinition();
		temp.setBeanClass(com.fost.ssacache.key.DefaultCacheKeyStoreStrategy.class);
		beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("cacheKeyStoreStrategy", temp));
		
		pvs.addPropertyValue(new PropertyValue("cacheKeyProvider", beanDefinition));
		
		return pvs;
	}
}
