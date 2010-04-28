package org.youisoft.config;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.xml.BeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.youisoft.support.DefaultElementBeanNameGenerator;

/**
 * 
 * @author Janly
 *
 */
public class CommonBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {


	public void registerBeanDefinitions(Document doc,XmlReaderContext readerContext) throws BeanDefinitionStoreException {
		BeanDefinitionParserDelegate delegate=new BeanDefinitionParserDelegate(readerContext);
		ParserContext parserContext=new ParserContext(readerContext,delegate,null);
		this.elementRead(doc.getDocumentElement(), parserContext);
	}
	
	
	private void elementRead(Element element,ParserContext parserContext){
		NamespaceHandler handler = parserContext.getReaderContext().getNamespaceHandlerResolver().resolve(element.getNamespaceURI());
		
		BeanDefinition containingBeanDefinition=parserContext.getContainingBeanDefinition();
		
		//local handle beanDefinition
		BeanDefinition beanDefinition=null;
		if(handler!=null){
			try{
				beanDefinition=handler.parse(element, parserContext);
			}catch(java.lang.Throwable t){
				//no operation
			}
			
			if(beanDefinition!=null) {
				BeanDefinitionHolder beanDefinitionHolder=new BeanDefinitionHolder(beanDefinition,this.resolveBeanName(element, parserContext,beanDefinition));
				NamedNodeMap attributes = element.getAttributes();
				for (int i = 0; i < attributes.getLength(); i++) {
					Node attribute = attributes.item(i);
					if(this.decorateIfNessaryForAttribute(element, attribute)){
						beanDefinitionHolder=handler.decorate(attribute, beanDefinitionHolder, parserContext);
					}
				}
				beanDefinition=beanDefinitionHolder.getBeanDefinition();
			}
		}
		
		if(beanDefinition!=null) containingBeanDefinition=beanDefinition;
		NodeList children=element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				this.elementRead((Element)node, new ParserContext(parserContext.getReaderContext(),parserContext.getDelegate(),containingBeanDefinition));
			}
		}
	}
	
	private boolean decorateIfNessaryForAttribute(Node element,Node attribute){
		return element.getNamespaceURI().equals(attribute.getNamespaceURI());
	}
	
	private String resolveBeanName(Element element,ParserContext parserContext,BeanDefinition beanDefinition){
		return DefaultElementBeanNameGenerator.getInstance().generateBeanName(element, beanDefinition, parserContext.getRegistry());
	}

}
