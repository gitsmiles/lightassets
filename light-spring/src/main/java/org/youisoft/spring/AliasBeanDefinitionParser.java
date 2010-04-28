/**
 * 
 */
package org.youisoft.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author Janly
 *
 */
public class AliasBeanDefinitionParser extends AbstractLightBeanDefinitionParser{
	public static final String ALIAS_ELEMENT = "alias";
	public static final String NAME_ATTRIBUTE = "name";
	public static final String ALIAS_ATTRIBUTE = "alias";

	public BeanDefinition parseInternal(Element element, ParserContext parserContext) {
		String name = element.getAttribute(NAME_ATTRIBUTE);
		String alias = element.getAttribute(ALIAS_ATTRIBUTE);
		boolean valid = true;
		if (!StringUtils.hasText(name)) {
			parserContext.getReaderContext().error("Name must not be empty", element);
			valid = false;
		}
		if (!StringUtils.hasText(alias)) {
			parserContext.getReaderContext().error("Alias must not be empty", element);
			valid = false;
		}
		if (valid) {
			try {
				parserContext.getReaderContext().getRegistry().registerAlias(name, alias);
			}catch (Exception ex) {
				parserContext.getReaderContext().error("Failed to register alias '" + alias +
						"' for bean with name '" + name + "'", element, ex);
			}
			parserContext.getReaderContext().fireAliasRegistered(name, alias, parserContext.extractSource(element));
		}
		return null;
	}

}
