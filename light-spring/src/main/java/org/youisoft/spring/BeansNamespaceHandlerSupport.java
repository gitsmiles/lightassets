/**
 * 
 */
package org.youisoft.spring;

import org.youisoft.support.NamespaceHandlerSupport;

/**
 * @author Janly
 *
 */
public class BeansNamespaceHandlerSupport extends NamespaceHandlerSupport {
	private static final String BEANS="beans";
	private static final String BEAN="bean";
	private static final String IMPORT="import";
	private static final String ALIAS="alias";

	public void init() {
		this.registerHolderDefinitionParser(BEANS, new BeansHolderDefinitionParser());
		this.registerHolderDefinitionParser(BEAN, new BeanHolderDefinitionParser());
		this.registerHolderDefinitionParser(IMPORT, new ImportHolderDefinitionParser());
		this.registerHolderDefinitionParser(ALIAS, new AliasHolderDefinitionParser());
	}

}
