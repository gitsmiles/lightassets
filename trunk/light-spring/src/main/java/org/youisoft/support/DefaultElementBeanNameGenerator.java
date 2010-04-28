/**
 * 
 */
package org.youisoft.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author Janly
 *
 */
public class DefaultElementBeanNameGenerator extends DefaultBeanNameGenerator implements ElementBeanNameGenerator {
	public static final String ID_ATTRIBUTE = "id";
	public static final String NAME_ATTRIBUTE = "name";
	public static final String BEAN_NAME_DELIMITERS = ",; ";
	private static final ElementBeanNameGenerator instance=new DefaultElementBeanNameGenerator();
	
	
	private DefaultElementBeanNameGenerator(){
		
	}
	
	public static ElementBeanNameGenerator getInstance(){
		return instance;
	}
	
	
	@Override
	public String generateBeanName(Element element, BeanDefinition definition,BeanDefinitionRegistry registry) {
		String id = element.getAttribute(ID_ATTRIBUTE);
		String nameAttr = element.getAttribute(NAME_ATTRIBUTE);
		List aliases = this.generateAlias(element, definition, registry);
		String beanName = id;
		if (!StringUtils.hasText(beanName) && !aliases.isEmpty()) {
			beanName = (String) aliases.remove(0);
		}
		if (!StringUtils.hasText(beanName)) {
			beanName = this.generateBeanName(definition, registry);
		}
		return beanName;
	}
	
	
	public List generateAlias(Element element,BeanDefinition definition,BeanDefinitionRegistry registry){
		List aliases = new ArrayList();
		String nameAttr = element.getAttribute(NAME_ATTRIBUTE);
		if (StringUtils.hasLength(nameAttr)) {
			String[] nameArr = StringUtils.tokenizeToStringArray(nameAttr, BEAN_NAME_DELIMITERS);
			aliases.addAll(Arrays.asList(nameArr));
		}
		return aliases;
	}


}
