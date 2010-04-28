/**
 * 
 */
package org.youisoft.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author Janly
 *
 */
public class BeanBeanDefinitionParser extends AbstractLightBeanDefinitionParser {

	public BeanDefinition parseInternal(Element element, ParserContext parserContext) {
		BeanDefinitionHolder bdh=parserContext.getDelegate().parseBeanDefinitionElement(element);
		return bdh.getBeanDefinition();
	}

}
