/**
 * 
 */
package org.youisoft.context;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.youisoft.config.CommonBeanDefinitionDocumentReader;

/**
 * @author Janly
 *
 */
public class XmlWebApplicationContext extends org.springframework.web.context.support.XmlWebApplicationContext{

	protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
		super.initBeanDefinitionReader(beanDefinitionReader);
		beanDefinitionReader.setDocumentReaderClass(CommonBeanDefinitionDocumentReader.class);
	}
}
