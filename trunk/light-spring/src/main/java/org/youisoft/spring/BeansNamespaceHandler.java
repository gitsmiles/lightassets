/**
 * 
 */
package org.youisoft.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * @author Janly
 *
 */
public class BeansNamespaceHandler extends NamespaceHandlerSupport {
	private static final String BEANS="beans";
	private static final String BEAN="bean";
	private static final String IMPORT="import";
	private static final String ALIAS="alias";

	public void init() {
		this.registerBeanDefinitionParser(BEANS, new BeansBeanDefinitionParser());
		this.registerBeanDefinitionParser(BEAN, new BeanBeanDefinitionParser());
		this.registerBeanDefinitionParser(IMPORT, new ImportBeanDefinitionParser());
		this.registerBeanDefinitionParser(ALIAS, new AliasBeanDefinitionParser());
	}

}
