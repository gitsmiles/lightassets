package com.fost.prop.service;

import java.io.File;

import org.dom4j.Document;

import com.fost.prop.api.model.Node;

public interface XMLService {
	public Document createXMLFile(Node node);
	public Node createNodeFromXML(File file);
}
