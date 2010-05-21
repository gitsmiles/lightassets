package com.fost.prop.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fost.prop.api.model.Node;
import com.fost.prop.dao.NodeDao;

@Repository("nodeDao")
public class NodeDaoImpl extends BaseDao implements NodeDao {
    @Override
    public int deleteNode(int id) {
        return getSqlMapClientTemplate().delete("NodeDao.deleteNode", Integer.valueOf(id));
    }

    @Override
    public Node findNode(int id) {
        return (Node) getSqlMapClientTemplate().queryForObject("NodeDao.findNode", Integer.valueOf(id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Node> findNodesByParentId(int parentId) {
        return getSqlMapClientTemplate().queryForList("NodeDao.findNodesByParentId", Integer.valueOf(parentId));
    }

    @Override
    public Node insertNode(Node node) {
        if(node.getId() <= 0){
            node.setId(getNodeId());
        }
        getSqlMapClientTemplate().insert("NodeDao.insertNode", node);
        return node;
    }

    @Override
    public int updateNode(Node node) {
        return getSqlMapClientTemplate().update("NodeDao.updateNode", node);
    }

    public int getNodeId() {
        return (Integer) getSqlMapClientTemplate().queryForObject("NodeDao.getNodeId");
    }
    
    public int updateVersion(int id){
        return (Integer) getSqlMapClientTemplate().update("NodeDao.updateVersion",id);
    }
    public void changId(Node node){
        if(node == null)
            return;
        List<Node> appList = findNodesByParentId(node.getId());
        for(int i=0;i<appList.size();i++){
            Node child = appList.get(i);
            child.setAppId(node.getId());
            try{
                updateNode(child);
            }catch(Exception e){
                e.printStackTrace();
            }
            changId(child);
        }
    }    
}
