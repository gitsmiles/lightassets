/**
 * 
 */
package org.youisoft.spring;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author Janly
 *
 */
public class BeansBeanDefinitionParser extends AbstractLightBeanDefinitionParser{


	@Override
	protected BeanDefinition parseInternal(Element element,ParserContext parserContext) {
		parserContext.getDelegate().initDefaults(element);
		return null;
	}
	
	
	
}
