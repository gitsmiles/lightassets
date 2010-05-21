package com.fost.prop.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fost.prop.api.model.Attribute;
import com.fost.prop.api.model.Node;
import com.fost.prop.service.XMLService;

@Service("xmlService")
public class XMLServiceImpl implements XMLService {
	private final static Logger LOGGER = LoggerFactory.getLogger(XMLServiceImpl.class);
	public Document createXMLFile(Node node) {

		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("root");
		rootElement.addAttribute("id", String.valueOf(node.getId()));
		rootElement.addAttribute("parentId", String.valueOf(node.getParentId()));

		createElement(rootElement, node);

		return document;
	}

	public Node createNodeFromXML(File file) {
		if(file == null) return null;
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}

		Element rootElm = document.getRootElement();
		Node root = new Node();

		patNode(root, rootElm);

		return root;
	}

	private void patNode(Node node, Element element) {
		if (element == null)
			return;
		node.setId(new Integer(element.attributeValue("id")));
		node.setParentId(new Integer(element.attributeValue("parentId")));

		Element propertyElement = (Element) element
				.selectSingleNode("proptery[@parentId='" + node.getId() + "']/value");
		node.setName(propertyElement.getTextTrim());

		List<Element> nodes = element.selectNodes("node[@parentId=" + node.getId() + "]");
		if (nodes != null && nodes.size() > 0) {
			List<Node> childList = new ArrayList<Node>();
			for (Element e : nodes) {
				Node newNode = new Node();
				patNode(newNode, e);
				childList.add(newNode);
			}
			node.setChildList(childList);
		}

		List<Element> attributes = element.selectNodes("attribute[@parentId='" + node.getId() + "']");
		if (attributes != null && attributes.size() > 0) {
			List<Attribute> attributeList = new ArrayList<Attribute>();
			for (Element e : attributes) {
				Attribute attribute = new Attribute();
				Element keyElem = (Element) e.selectSingleNode("key");
				Element valueElem = (Element) e.selectSingleNode("value");
				Element memoElem = (Element) e.selectSingleNode("memo");
				attribute.setKey(keyElem.getTextTrim());
				attribute.setValue(valueElem.getTextTrim());
				if (memoElem != null)
					attribute.setMemo(memoElem.getTextTrim());
				attributeList.add(attribute);
			}
			node.setAttributeList(attributeList);
		}
	}

	private void createElement(Element element, Object obj) {
		if (obj == null)
			return;

		Node node = (Node) obj;
		element.addElement("proptery").addAttribute("name", "name").addAttribute("parentId",
				String.valueOf(node.getId())).addElement("value").setText(node.getName());

		if (node.getChildList() != null && node.getChildList().size() > 0) {
			for (Node n : node.getChildList()) {
				Element nodeElement = element.addElement("node");
				nodeElement.addAttribute("id", String.valueOf(n.getId()));
				nodeElement.addAttribute("parentId", String.valueOf(n.getParentId()));
				createElement(nodeElement, n);
			}
		}

		if (node.getAttributeList() != null && node.getAttributeList().size() > 0) {
			for (Attribute a : node.getAttributeList()) {
				Element attributeElement = element.addElement("attribute").addAttribute("parentId",
						String.valueOf(node.getId()));
				attributeElement.addElement("key").addCDATA(a.getKey());
				attributeElement.addElement("value").addCDATA(a.getValue());
				if (a.getMemo() != null && !a.getMemo().equals("")) {
					attributeElement.addElement("memo").addCDATA(a.getMemo());
				}
			}
		}
	}
}
