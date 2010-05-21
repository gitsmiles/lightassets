package com.fost.prop.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fost.prop.api.model.Node;
import com.fost.prop.dao.NodeDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao.xml" })
public class NodeDaoImplTest {
    @Autowired
    private NodeDao nodeDao;

//    @Test
//    public void test1(){
//        Node node = new Node();
//        node.setName("test");
//        node.setRootId(333);
//        
//        nodeDao.insertNode(node);
//    }

    @Test
    public void updateVersion(){
        nodeDao.updateVersion(105);
    }

}
