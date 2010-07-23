package com.fost.ssacache.config;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Janly
 *
 */
public class ClusterBeanDefinitionParser extends BaseBeanDefinitionParser{

	@Override
	protected MutablePropertyValues parseSsaContextBeanPropertyDefinition(
			Element element, ParserContext parserContext) {
		
		MutablePropertyValues pvs=new MutablePropertyValues();
		
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.impl.ClusterCacheFactory.class);
		
		
		GenericBeanDefinition gbd=null;
		NodeList nl=element.getChildNodes();
		int len=nl.getLength();
		ManagedList ml=new ManagedList(len);
		for(int i=0;i<len;i++){
			gbd=new GenericBeanDefinition();
			NamedNodeMap nnm=nl.item(i).getAttributes();
			gbd.setBeanClassName(nnm.getNamedItem("adapter").getNodeValue());
			PropertyValue ppv=new PropertyValue("group",nnm.getNamedItem("group").getNodeValue());
			gbd.getPropertyValues().addPropertyValue(ppv);
			
			ppv=new PropertyValue("local",nnm.getNamedItem("local").getNodeValue());
			gbd.getPropertyValues().addPropertyValue(ppv);
			
			ppv=new PropertyValue("client",new RuntimeBeanReference(nnm.getNamedItem("name").getNodeValue()));
			gbd.getPropertyValues().addPropertyValue(ppv);
			ml.add(gbd);
		}
		
		beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("clients",ml));
		
		
		
		
		PropertyValue pv = new PropertyValue("cacheFactory", beanDefinition);
		pvs.addPropertyValue(pv);
		
		
		////////////////////////////////
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
