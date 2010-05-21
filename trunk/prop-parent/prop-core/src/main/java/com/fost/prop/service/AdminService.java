package com.fost.prop.service;

import java.io.File;
import java.util.List;

import org.dom4j.Document;

import com.fost.prop.api.model.Attribute;
import com.fost.prop.api.model.Node;
import com.fost.prop.model.Operator;

public interface AdminService {
	Object addAttribute(Attribute attr,Operator op);

	Node addNode(Node node,Operator op);

	List<Node> findNodesByParentId(int nodeId);

	List<Attribute> findAttributesByNodeId(int nodeId);

	Attribute findAttributeById(int id);

	Node findNodeById(int nodeId);

	int updateAttribute(Attribute attr, Operator op);

	int updateNode(Node node, Operator op);

	int deleteAttributeById(int attributeId, Operator op);

	int deleteNodeById(int nodeId, Operator op);

	void moveUp(int nodeId, int attributeId);

	void moveDown(int nodeId, int attributeId);

	void xml2database(int nodeId, File file, Operator op);

	Document database2xml(int nodeId, Operator op);

}
