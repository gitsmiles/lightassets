package com.fost.ssacache.config;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public abstract class BaseBeanDefinitionParser implements BeanDefinitionParser{
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.SsaContext.class);
		beanDefinition.setAbstract(true);
		java.util.List<PropertyValue> pv=this.parseSsaContextBeanPropertyDefinition(element,parserContext);
		for(java.util.Iterator<PropertyValue> it=pv.iterator();it.hasNext();){
			beanDefinition.getPropertyValues().addPropertyValue(it.next());
		}
		parserContext.getRegistry().registerBeanDefinition("ssaContext", beanDefinition);
		
		this.parseSsaContextSubDefinition("ssaContext", parserContext);
		return null;
	}
	
	protected abstract java.util.List<PropertyValue> parseSsaContextBeanPropertyDefinition(Element element, ParserContext parserContext);
	
	
	public void parseSsaContextSubDefinition(String parentName, ParserContext parserContext) {
		GenericBeanDefinition beanDefinition=null;
		
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.aspect.ReadFromCacheAspect.class);
		beanDefinition.setParentName(parentName);
		parserContext.getRegistry().registerBeanDefinition("readFromCacheAspect", beanDefinition);
		
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.aspect.GetFromCacheAspect.class);
		beanDefinition.setParentName(parentName);
		parserContext.getRegistry().registerBeanDefinition("getFromCacheAspect", beanDefinition);
		
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.aspect.AddToCacheAspect.class);
		beanDefinition.setParentName(parentName);
		parserContext.getRegistry().registerBeanDefinition("addToCacheAspect", beanDefinition);
		
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.aspect.SetToCacheAspect.class);
		beanDefinition.setParentName(parentName);
		parserContext.getRegistry().registerBeanDefinition("setToCacheAspect", beanDefinition);
		
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.aspect.DeleteFromCacheAspect.class);
		beanDefinition.setParentName(parentName);
		parserContext.getRegistry().registerBeanDefinition("deleteFromCacheAspect", beanDefinition);
		
	}
}
