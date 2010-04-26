/**
 * 
 */
package org.youisoft;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author Janly
 *
 */
public interface NamespaceHandler extends org.springframework.beans.factory.xml.NamespaceHandler{
	public HolderDefinition parseHolder(Element element, ParserContext parserContext);
}
