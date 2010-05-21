package com.fost.prop.dao.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fost.prop.api.model.Attribute;
import com.fost.prop.api.model.Node;
import com.fost.prop.dao.AttributeDao;
import com.fost.prop.dao.NodeDao;
import com.fost.prop.service.TreeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao.xml","classpath:spring/applicationContext-service.xml"})
public class PropertiesServiceImpTest {
    @Autowired
    private TreeService ts;
    
    @Autowired
    private NodeDao nodeDao;
    
    @Autowired
    private AttributeDao attributeDao;

    @Test
    public void test1(){
//    	Node root = ts.getTree("dd", -1);
//    	Node node = null;
//
//    	for(int i=0;i<root.getChildList().size();i++){
//    		Node n = root.getChildList().get(i);
//    		if(n.getName().equals("equipment")){
//    			node = n;
//    			break;
//    		}
//    	}
//
//    	if(node == null) return;
//
//    	node.setName("money");// 应用节点要先改个名字   
//    	node.setAppId(0);// 在没取到节点ID前，一定要设置为0；
//
//    	insertData(node);
    }

    /*
     * 将一颗应用树完全复制一份，
     */
    public void insertData(Node node){
    	if(node == null) return;

    	node.setId(0);//  把节点的ID先干掉

    	List<Node> childrenNode = node.getChildList();
    	List<Attribute> attributes = node.getAttributeList();

    	Node ret = nodeDao.insertNode(node); // 先把当前节点插入
    	if(ret.getAppId() == 0){// 根节点要设置appId 为  nodeId
    		ret.setAppId(ret.getId());
    	}

    	for(Attribute a : attributes){// 把对应的属性节点插入
    		a.setNodeId(ret.getId());// 设置父节点
    		a.setId(0);// 把已经有的节点ID干掉
    		a.setAppId(ret.getAppId());
    		attributeDao.insertAttribute(a);
    	}

    	for(Node n : childrenNode){
    		n.setParentId(ret.getId());
    		n.setAppId(ret.getAppId());
    		insertData(n);
    	}
    }
}
