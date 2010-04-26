/**
 * 
 */
package org.youisoft;
import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinitionHolder;

/**
 * @author Janly
 *
 */
public interface HolderDefinition extends BeanMetadataElement{

	
	public BeanDefinitionHolder[] getBeanDefinitionHolders();
	
	public void setBeanMetadataElement(Object key,Object obj);
	
	public Object getBeanMetadataElement(Object key);
	
	
}
