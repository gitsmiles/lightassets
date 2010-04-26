/**
 * 
 */
package org.youisoft.spring;

import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.youisoft.HolderDefinition;
import org.youisoft.HolderDefinitionParser;

/**
 * @author Janly
 *
 */
public class BeansHolderDefinitionParser implements HolderDefinitionParser{

	public HolderDefinition parse(Element element, ParserContext parserContext) {
		parserContext.getDelegate().initDefaults(element);
		return null;
	}
}