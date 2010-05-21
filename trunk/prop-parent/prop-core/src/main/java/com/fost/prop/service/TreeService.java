package com.fost.prop.service;

import com.fost.prop.api.model.Node;

public interface TreeService {
    public Node getTree(String applicationName, int version);
    public void printTree(Node node);
    public Node loadTree(int nodeId);
}
