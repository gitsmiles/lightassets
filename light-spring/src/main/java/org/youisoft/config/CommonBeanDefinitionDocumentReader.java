package org.youisoft.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.youisoft.HolderDefinition;

/**
 * 
 * @author Janly
 *
 */
public class CommonBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {
	public static final String ID_ATTRIBUTE = "id";
	public static final String NAME_ATTRIBUTE = "name";
	public static final String BEAN_NAME_DELIMITERS = ",; ";

	public void registerBeanDefinitions(Document doc,XmlReaderContext readerContext) throws BeanDefinitionStoreException {
		BeanDefinitionParserDelegate delegate=new BeanDefinitionParserDelegate(readerContext);
		ParserContext parserContext=new ParserContext(readerContext,delegate);
		this.elementRead(doc.getDocumentElement(), parserContext);
	}
	
	
	private void elementRead(Element ele,ParserContext parserContext){
		boolean push=false;
		try{
			NamespaceHandler handler = parserContext.getReaderContext().getNamespaceHandlerResolver().resolve(ele.getNamespaceURI());
			CompositeHolderDefinition chd=null;
			if (handler != null) {
				if(handler instanceof org.youisoft.NamespaceHandler){
					HolderDefinition cd=((org.youisoft.NamespaceHandler)handler).parseHolder(ele, parserContext);
					if(cd!=null){
						if(cd instanceof CompositeHolderDefinition){
							chd=(CompositeHolderDefinition)cd;
						}else{
							chd=new CompositeHolderDefinition(ele.getTagName(),parserContext.extractSource(ele),cd);
						}
					}
				}else{
					BeanDefinition bd=handler.parse(ele, parserContext);
					if(bd!=null){
						chd=new CompositeHolderDefinition(ele.getTagName(),parserContext.extractSource(ele));
						String id = ele.getAttribute(ID_ATTRIBUTE);
						String nameAttr = ele.getAttribute(NAME_ATTRIBUTE);

						List aliases = new ArrayList();
						if (StringUtils.hasLength(nameAttr)) {
							String[] nameArr = StringUtils.tokenizeToStringArray(nameAttr, BEAN_NAME_DELIMITERS);
							aliases.addAll(Arrays.asList(nameArr));
						}

						String beanName = id;
						if (!StringUtils.hasText(beanName) && !aliases.isEmpty()) {
							beanName = (String) aliases.remove(0);
						}
						
						if (!StringUtils.hasText(beanName)) {
							beanName = parserContext.getReaderContext().generateBeanName(bd);
							String beanClassName = bd.getBeanClassName();
							if (beanClassName != null &&beanName.startsWith(beanClassName) && beanName.length() > beanClassName.length() &&
									!parserContext.getReaderContext().getRegistry().isBeanNameInUse(beanClassName)) {
								aliases.add(beanClassName);
							}
						}
						
						String[] aliasesArray = StringUtils.toStringArray(aliases);
						BeanDefinitionHolder temp=new BeanDefinitionHolder(bd, beanName, aliasesArray);
						chd.addBeanDefinitionHolder(temp);
						
					}

				}
			}
			
			
			/////

			if(chd!=null) {
				NamedNodeMap attributes = ele.getAttributes();
				for (int i = 0; i < attributes.getLength(); i++) {
					Node node = attributes.item(i);
					chd=decorate(node,parserContext,chd);
				}
			}
			
			if(chd!=null){
				parserContext.pushContainingComponent(chd);
				push=true;
			}
			
			//////////
			
			
			NodeList children=ele.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node node = children.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					this.elementRead((Element)node, parserContext);
				}
			}
		}finally{
			if(push) {
				CompositeComponentDefinition ccd=parserContext.popContainingComponent();
				if(ccd instanceof CompositeHolderDefinition){
					BeanDefinitionHolder[] bdhs=((CompositeHolderDefinition)ccd).getBeanDefinitionHolders();
					for(int i=0;i<bdhs.length;i++){
						parserContext.getRegistry().registerBeanDefinition(bdhs[i].getBeanName(), bdhs[i].getBeanDefinition());
						String[] als=bdhs[i].getAliases();
						for(int j=0;j<als.length;j++){
							parserContext.getRegistry().registerAlias(bdhs[i].getBeanName(), als[i]);
						}
					}
				}
			}
		}
	}
	

	private CompositeHolderDefinition decorate(Node node,ParserContext parserContext,CompositeHolderDefinition originalDef){
		CompositeHolderDefinition hd=originalDef;
		NamespaceHandler handler = parserContext.getReaderContext().getNamespaceHandlerResolver().resolve(node.getNamespaceURI());
		if (handler != null) {
			BeanDefinitionHolder[] bdhs=originalDef.getBeanDefinitionHolders();
			BeanDefinitionHolder[] dest=new BeanDefinitionHolder[bdhs.length];
			for(int i=0;i<bdhs.length;i++){
				dest[i]=handler.decorate(node, bdhs[i], parserContext);
				hd.addBeanDefinitionHolder(dest[i]);
			}	
		}
		return hd;
	}
	
}
