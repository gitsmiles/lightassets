package com.fost.prop.web.wapper;

import com.fost.prop.api.model.Node;
import com.fost.prop.model.Operator;


public interface AdminServiceWapper {
    public Node addNode(Node node,Operator opuser);

    public int deleteNodeById(int nodeId,Operator opuser);
}
