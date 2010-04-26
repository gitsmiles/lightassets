/**
 * 
 */
package org.youisoft.spring;

import org.springframework.beans.factory.xml.DocumentDefaultsDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.youisoft.HolderDefinition;
import org.youisoft.HolderDefinitionParser;
import org.youisoft.config.CompositeHolderDefinition;

/**
 * @author Janly
 *
 */
public class BeansHolderDefinitionParser implements HolderDefinitionParser{
	public static final String DEFAULT_LAZY_INIT_ATTRIBUTE = "default-lazy-init";

	public static final String DEFAULT_MERGE_ATTRIBUTE = "default-merge";

	public static final String DEFAULT_AUTOWIRE_ATTRIBUTE = "default-autowire";

	public static final String DEFAULT_DEPENDENCY_CHECK_ATTRIBUTE = "default-dependency-check";

	public static final String DEFAULT_AUTOWIRE_CANDIDATES_ATTRIBUTE = "default-autowire-candidates";

	public static final String DEFAULT_INIT_METHOD_ATTRIBUTE = "default-init-method";

	public static final String DEFAULT_DESTROY_METHOD_ATTRIBUTE = "default-destroy-method";

	public HolderDefinition parse(Element root, ParserContext parserContext) {
		DocumentDefaultsDefinition defaults = new DocumentDefaultsDefinition();
		defaults.setLazyInit(root.getAttribute(DEFAULT_LAZY_INIT_ATTRIBUTE));
		defaults.setMerge(root.getAttribute(DEFAULT_MERGE_ATTRIBUTE));
		defaults.setAutowire(root.getAttribute(DEFAULT_AUTOWIRE_ATTRIBUTE));
		defaults.setDependencyCheck(root.getAttribute(DEFAULT_DEPENDENCY_CHECK_ATTRIBUTE));
		if (root.hasAttribute(DEFAULT_AUTOWIRE_CANDIDATES_ATTRIBUTE)) {
			defaults.setAutowireCandidates(root.getAttribute(DEFAULT_AUTOWIRE_CANDIDATES_ATTRIBUTE));
		}
		if (root.hasAttribute(DEFAULT_INIT_METHOD_ATTRIBUTE)) {
			defaults.setInitMethod(root.getAttribute(DEFAULT_INIT_METHOD_ATTRIBUTE));
		}
		if (root.hasAttribute(DEFAULT_DESTROY_METHOD_ATTRIBUTE)) {
			defaults.setDestroyMethod(root.getAttribute(DEFAULT_DESTROY_METHOD_ATTRIBUTE));
		}
		defaults.setSource(parserContext.getReaderContext().extractSource(root));

		parserContext.getReaderContext().fireDefaultsRegistered(defaults);
		
		CompositeHolderDefinition chd=new CompositeHolderDefinition(root.getTagName(),parserContext.extractSource(root));
		chd.setBeanMetadataElement("", defaults);
		return chd;
	}
	
	

}
