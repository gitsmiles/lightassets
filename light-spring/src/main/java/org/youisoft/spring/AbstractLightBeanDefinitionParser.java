/**
 * 
 */
package org.youisoft.spring;

import java.util.List;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.youisoft.support.DefaultElementBeanNameGenerator;

/**
 * @author Janly
 *
 */
public abstract class AbstractLightBeanDefinitionParser implements BeanDefinitionParser {
	
	public final BeanDefinition parse(Element element, ParserContext parserContext) {
		BeanDefinition definition = parseInternal(element, parserContext);
		if(definition!=null){
			List aliases=DefaultElementBeanNameGenerator.getInstance().generateAlias(element, definition, parserContext.getRegistry());
			BeanDefinitionHolder holder = new BeanDefinitionHolder(definition, 
					DefaultElementBeanNameGenerator.getInstance().generateBeanName(element, definition, parserContext.getRegistry()),
					(String[])aliases.toArray(new String[aliases.size()]));
			registerBeanDefinition(holder, parserContext.getRegistry());
			if (shouldFireEvents()) {
				BeanComponentDefinition componentDefinition = new BeanComponentDefinition(holder);
				parserContext.registerComponent(componentDefinition);
			}
		}
		return definition;
	}




	protected void registerBeanDefinition(BeanDefinitionHolder definition, BeanDefinitionRegistry registry) {
		BeanDefinitionReaderUtils.registerBeanDefinition(definition, registry);
	}



	protected abstract BeanDefinition parseInternal(Element element, ParserContext parserContext);




	protected boolean shouldFireEvents() {
		return true;
	}

}
