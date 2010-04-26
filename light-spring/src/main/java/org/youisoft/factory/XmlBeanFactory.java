/**
 * 
 */
package org.youisoft.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.youisoft.config.CommonBeanDefinitionDocumentReader;

/**
 * @author Janly
 *
 */
public class XmlBeanFactory extends DefaultListableBeanFactory{
	
	public XmlBeanFactory(Resource resource) throws BeansException {
		this(resource, null);
	}

	public XmlBeanFactory(Resource resource, BeanFactory parentBeanFactory) throws BeansException {
		super(parentBeanFactory);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);
		reader.setDocumentReaderClass(CommonBeanDefinitionDocumentReader.class);
		reader.loadBeanDefinitions(resource);
	}

}
