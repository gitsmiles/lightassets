package com.fost.prop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fost.prop.api.model.Attribute;
import com.fost.prop.api.model.Node;
import com.fost.prop.dao.AttributeDao;
import com.fost.prop.dao.NodeDao;
import com.fost.prop.service.TreeService;

@Service("treeService")
public class TreeServiceImpl implements TreeService {
    @Autowired
    private NodeDao nodeDao;

    @Autowired
    private AttributeDao attributeDao;

    private Node loadTree() {
        List<Node> list = nodeDao.findNodesByParentId(-1);
        Node root = list.get(0);
        return buildTree(root);
    }
    
    // 得到一棵子树，子树的根节点ID 为nodeId
    public Node loadTree(int nodeId){
    	Node node = nodeDao.findNode(nodeId);
    	return buildTree(node);
    }

    /*
     * 得到整棵树
     * 
     */
    public Node getTree(String applicationName, int version) {
        return loadTree();
    }

    private Node buildTree(Node node) {
        if (node == null) {
            return node;
        }
        try {
            List<Node> childList = nodeDao.findNodesByParentId(node.getId());
            List<Attribute> atrributeList = attributeDao.findAttributesByNodeId(node.getId());
            node.setAttributeList(atrributeList);
            node.setChildList(childList);
            for (int i = 0; i < childList.size(); i++) {
                Node nd = childList.get(i);
                buildTree(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return node;
    }

    public void printTree(Node node) {
        if (node == null)
            return;
        System.out.println(node.getName());
        List<Node> list = node.getChildList();
        if(list != null){
            for (int i = 0; i < list.size(); i++) {
                Node nd = list.get(i);
                printTree(nd);
            }       	
        }

        List<Attribute> attributeList = node.getAttributeList();
        if(attributeList != null){
        	for(Attribute a : attributeList){
        		System.out.println(a.getKey()+" : "+a.getValue());
        	}
        }
    }
}
