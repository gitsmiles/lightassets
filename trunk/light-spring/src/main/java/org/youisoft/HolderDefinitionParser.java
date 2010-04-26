package org.youisoft;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author Janly
 *
 */
public interface HolderDefinitionParser{
	public HolderDefinition parse(Element element, ParserContext parserContext);
}
