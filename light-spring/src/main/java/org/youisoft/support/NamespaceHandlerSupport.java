package org.youisoft.support;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;
import org.youisoft.HolderDefinition;
import org.youisoft.HolderDefinitionParser;
import org.youisoft.NamespaceHandler;
import org.springframework.beans.factory.xml.ParserContext;


/**
 * 
 * @author Janly
 *
 */
public abstract class NamespaceHandlerSupport extends org.springframework.beans.factory.xml.NamespaceHandlerSupport implements NamespaceHandler {
	private final Map holderParsers = new HashMap();
	

	private HolderDefinitionParser findParserForElement(Element element, ParserContext parserContext) {
		HolderDefinitionParser parser = (HolderDefinitionParser) this.holderParsers.get(element.getLocalName());
		return parser;
	}
	
	public HolderDefinition parseHolder(Element element,ParserContext parserContext) {
		HolderDefinitionParser parser=findParserForElement(element, parserContext);
		if(parser!=null) parser.parse(element, parserContext);
		return null;
	}
	
	protected final void registerHolderDefinitionParser(String elementName, HolderDefinitionParser parser) {
		this.holderParsers.put(elementName, parser);
	}
}
