package com.fost.prop.dao;

import java.util.List;

import com.fost.prop.api.model.Node;

public interface NodeDao {
    Node insertNode(Node node);

    int updateNode(Node node);

    int deleteNode(int id);

    Node findNode(int id);

    List<Node> findNodesByParentId(int parentId);
    
    int getNodeId();
    
    int updateVersion(int nodeId);
}
