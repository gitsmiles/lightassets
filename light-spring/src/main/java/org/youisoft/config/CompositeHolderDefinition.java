/**
 * 
 */
package org.youisoft.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.youisoft.HolderDefinition;

/**
 * @author Janly
 *
 */
public class CompositeHolderDefinition extends CompositeComponentDefinition implements HolderDefinition{
	private final List beanDefinitionHolders = new LinkedList();
	private final Map properties = new HashMap();

	public CompositeHolderDefinition(String name, Object source) {
		super(name,source);
	}
	
	public CompositeHolderDefinition(String name, Object source,HolderDefinition holderDefinition) {
		super(name,source);
		BeanDefinitionHolder[] bdh=holderDefinition.getBeanDefinitionHolders();
		System.arraycopy(bdh, 0, beanDefinitionHolders, 0, bdh.length);
		
	}
	

	public BeanDefinitionHolder[] getBeanDefinitionHolders() {
		return (BeanDefinitionHolder[])this.beanDefinitionHolders.toArray(new BeanDefinitionHolder[this.beanDefinitionHolders.size()]);
	}
	
	public BeanDefinitionHolder addBeanDefinitionHolder(BeanDefinitionHolder beanDefinitionHolder){
		this.beanDefinitionHolders.add(beanDefinitionHolder);
		return beanDefinitionHolder;
	}

	public Object getBeanMetadataElement(Object key) {
		return this.properties.get(key);
	}

	public void setBeanMetadataElement(Object key, Object obj) {
		this.properties.put(key, obj);
	}
	
	
}
