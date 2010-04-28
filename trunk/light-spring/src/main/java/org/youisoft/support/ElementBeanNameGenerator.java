/**
 * 
 */
package org.youisoft.support;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.w3c.dom.Element;

/**
 * @author Janly
 *
 */
public interface ElementBeanNameGenerator extends BeanNameGenerator {

	public String generateBeanName(Element element,BeanDefinition definition,BeanDefinitionRegistry registry);
	
	public List generateAlias(Element element,BeanDefinition definition,BeanDefinitionRegistry registry);

}
