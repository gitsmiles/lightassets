/**
 * 
 */
package org.youisoft.spring;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.youisoft.HolderDefinition;
import org.youisoft.HolderDefinitionParser;
import org.youisoft.config.CompositeHolderDefinition;

/**
 * @author Janly
 *
 */
public class BeanHolderDefinitionParser implements HolderDefinitionParser {

	public HolderDefinition parse(Element element, ParserContext parserContext) {
		CompositeHolderDefinition chd=new CompositeHolderDefinition(element.getTagName(),parserContext.extractSource(element));
		BeanDefinitionHolder bdh=parserContext.getDelegate().parseBeanDefinitionElement(element);
		chd.addBeanDefinitionHolder(bdh);
		return chd;
	}

}
