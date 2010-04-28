/**
 * 
 */
package org.youisoft.spring;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;
import org.w3c.dom.Element;

/**
 * @author Janly
 *
 */
public class ImportBeanDefinitionParser extends AbstractLightBeanDefinitionParser{
	public static final String IMPORT_ELEMENT = "import";
	public static final String RESOURCE_ATTRIBUTE = "resource";

	public BeanDefinition parseInternal(Element element, ParserContext parserContext) {
		String location = element.getAttribute(RESOURCE_ATTRIBUTE);
		if (!StringUtils.hasText(location)) {
			parserContext.getReaderContext().error("Resource location must not be empty", element);
			return null;
		}
		location = SystemPropertyUtils.resolvePlaceholders(location);

		if (ResourcePatternUtils.isUrl(location)) {
			try {
				Set actualResources = new LinkedHashSet(4);
				parserContext.getReaderContext().getReader().loadBeanDefinitions(location, actualResources);
				Resource[] actResArray = (Resource[]) actualResources.toArray(new Resource[actualResources.size()]);
				parserContext.getReaderContext().fireImportProcessed(location, actResArray, parserContext.extractSource(element));
			}catch (BeanDefinitionStoreException ex) {
				parserContext.getReaderContext().error("Failed to import bean definitions from URL location [" + location + "]", element, ex);
			}
		}
		else {
			// No URL -> considering resource location as relative to the current file.
			try {
				Resource relativeResource = parserContext.getReaderContext().getResource().createRelative(location);
				parserContext.getReaderContext().getReader().loadBeanDefinitions(relativeResource);
				parserContext.getReaderContext().fireImportProcessed(location, new Resource[] {relativeResource}, parserContext.extractSource(element));
			}catch (IOException ex) {
				parserContext.getReaderContext().error("Invalid relative resource location [" + location + "] to import bean definitions from", element, ex);
			}catch (BeanDefinitionStoreException ex) {
				parserContext.getReaderContext().error("Failed to import bean definitions from relative location [" + location + "]", element, ex);
			}
		}
		return null;
	}

}
