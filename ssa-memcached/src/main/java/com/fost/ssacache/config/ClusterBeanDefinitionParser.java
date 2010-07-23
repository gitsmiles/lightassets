package com.fost.ssacache.config;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Janly
 *
 */
public class ClusterBeanDefinitionParser extends BaseBeanDefinitionParser{

	@Override
	protected MutablePropertyValues parseSsaContextBeanPropertyDefinition(Element element, ParserContext parserContext) {
		
		MutablePropertyValues pvs=new MutablePropertyValues();
		
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.impl.ClusterCacheFactory.class);
		
		//parse attribute
		
		beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("name",element.getAttribute("name")));
		beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("mode",element.getAttribute("mode")));
		
		//parse client element
		GenericBeanDefinition gbd=null;
		NodeList nl=element.getChildNodes();
		int len=nl.getLength();
		ManagedList ml=new ManagedList(len);
		for(int i=0;i<len;i++){
			Node node=nl.item(i);
			if(node.getNodeType()==Node.ELEMENT_NODE){
				Element ele=(Element)node;
				gbd=new GenericBeanDefinition();
				gbd.setBeanClassName(ele.getAttribute("adapter"));
				gbd.getPropertyValues().addPropertyValue(new PropertyValue("group",ele.getAttribute("group")));
				gbd.getPropertyValues().addPropertyValue(new PropertyValue("local",ele.getAttribute("local")));
				gbd.getPropertyValues().addPropertyValue(new PropertyValue("client",new RuntimeBeanReference(ele.getAttribute("name"))));
				ml.add(gbd);
			}

		}
		
		beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("clients",ml));

		pvs.addPropertyValue(new PropertyValue("cacheFactory", beanDefinition));
		
		
		////////////////////////////////
		beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(com.fost.ssacache.key.DefaultCacheKeyProvider.class);
		
		GenericBeanDefinition temp = new GenericBeanDefinition();
		temp.setBeanClass(com.fost.ssacache.key.DefaultCacheKeyStoreStrategy.class);
		beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("cacheKeyStoreStrategy", temp));

		pvs.addPropertyValue(new PropertyValue("cacheKeyProvider", beanDefinition));
		
		return pvs;
	}

}
