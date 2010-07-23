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
public class FostBeanDefinitionParser extends BaseBeanDefinitionParser{

	@Override
	protected MutablePropertyValues parseSsaContextBeanPropertyDefinition(Element element, ParserContext parserContext) {
		MutablePropertyValues pvs=new MutablePropertyValues();
		
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.impl.FostCacheServiceCacheFactory.class);
		
		PropertyValue pv = new PropertyValue("cacheFactory", beanDefinition);
		pvs.addPropertyValue(pv);
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.key.DefaultCacheKeyProvider.class);
		
		
		GenericBeanDefinition temp = new GenericBeanDefinition();
		temp.setBeanClass(com.fost.ssacache.key.DefaultCacheKeyStoreStrategy.class);
		pv = new PropertyValue("cacheKeyStoreStrategy", temp);
		beanDefinition.getPropertyValues().addPropertyValue(pv);
		

		pv = new PropertyValue("cacheKeyProvider", beanDefinition);
		pvs.addPropertyValue(pv);
		
		return pvs;
	}
	
	

	
}
