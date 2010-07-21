package com.fost.ssacache.config;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 
 * @author Janly
 *
 */
public abstract class BaseBeanDefinitionParser implements BeanDefinitionParser{
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		
		Object source=parserContext.extractSource(element);
		org.springframework.beans.factory.parsing.CompositeComponentDefinition ccd=
			new org.springframework.beans.factory.parsing.CompositeComponentDefinition(element.getTagName(),source);
		
		parserContext.pushContainingComponent(ccd);
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.SsaContext.class);
		beanDefinition.setAbstract(true);
		java.util.List<PropertyValue> pv=this.parseSsaContextBeanPropertyDefinition(element,parserContext);
		for(java.util.Iterator<PropertyValue> it=pv.iterator();it.hasNext();){
			beanDefinition.getPropertyValues().addPropertyValue(it.next());
		}
		
		org.springframework.beans.factory.config.BeanDefinitionHolder bdh=
			new org.springframework.beans.factory.config.BeanDefinitionHolder(beanDefinition,"ssaContext");
		
		org.springframework.beans.factory.parsing.BeanComponentDefinition bcd=
			new org.springframework.beans.factory.parsing.BeanComponentDefinition(bdh);
		
		
		parserContext.registerBeanComponent(bcd);
		
		
		this.parseSsaContextSubDefinition("ssaContext", parserContext);
		
		
		parserContext.popAndRegisterContainingComponent();
		
		return null;
	}
	
	protected abstract java.util.List<PropertyValue> parseSsaContextBeanPropertyDefinition(Element element, ParserContext parserContext);
	
	
	public void parseSsaContextSubDefinition(String parentName, ParserContext parserContext) {
		GenericBeanDefinition beanDefinition=null;
		org.springframework.beans.factory.config.BeanDefinitionHolder bdh=null;
		org.springframework.beans.factory.parsing.BeanComponentDefinition bcd=null;
		
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.aspect.ReadFromCacheAspect.class);
		beanDefinition.setParentName(parentName);
		bdh=new org.springframework.beans.factory.config.BeanDefinitionHolder(beanDefinition,"readFromCacheAspect");
		bcd=new org.springframework.beans.factory.parsing.BeanComponentDefinition(bdh);
		parserContext.registerBeanComponent(bcd);
		

		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.aspect.GetFromCacheAspect.class);
		beanDefinition.setParentName(parentName);
		bdh=new org.springframework.beans.factory.config.BeanDefinitionHolder(beanDefinition,"getFromCacheAspect");
		bcd=new org.springframework.beans.factory.parsing.BeanComponentDefinition(bdh);
		parserContext.registerBeanComponent(bcd);
		
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.aspect.AddToCacheAspect.class);
		beanDefinition.setParentName(parentName);
		bdh=new org.springframework.beans.factory.config.BeanDefinitionHolder(beanDefinition,"addToCacheAspect");
		bcd=new org.springframework.beans.factory.parsing.BeanComponentDefinition(bdh);
		parserContext.registerBeanComponent(bcd);
		
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.aspect.SetToCacheAspect.class);
		beanDefinition.setParentName(parentName);
		bdh=new org.springframework.beans.factory.config.BeanDefinitionHolder(beanDefinition,"setToCacheAspect");
		bcd=new org.springframework.beans.factory.parsing.BeanComponentDefinition(bdh);
		parserContext.registerBeanComponent(bcd);
		
		
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.aspect.DeleteFromCacheAspect.class);
		beanDefinition.setParentName(parentName);
		bdh=new org.springframework.beans.factory.config.BeanDefinitionHolder(beanDefinition,"deleteFromCacheAspect");
		bcd=new org.springframework.beans.factory.parsing.BeanComponentDefinition(bdh);
		parserContext.registerBeanComponent(bcd);
		
	}
}
