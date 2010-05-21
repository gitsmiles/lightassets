package com.fost.prop.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fost.prop.api.PropertiesService;
import com.fost.prop.api.model.Node;
import com.fost.prop.service.TreeService;

@Service("propertiesService")
public class PropertiesServiceImpl implements PropertiesService {
    @Autowired
    private TreeService treeService;

    @Override
    public Node getPropertiesTree(String applicationName, int version) {
        Node node = treeService.getTree(applicationName, version);
        Node rev = null;
        List<Node> list = node.getChildList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(applicationName)) {
                rev = list.get(i);
                break;
            }
        }
        if (rev.getVersion() == version) {
            rev = null;
        }
        return rev;
    }

    public Node getTree(String[] applicationNames,int[] versions){
    	Node ret = new Node();
    	List<Node> retChildList = new ArrayList<Node>();
    	
    	Node node = treeService.getTree(applicationNames[0], versions[0]);
    	List<Node> list = node.getChildList();

    	boolean versionChange = false;
    	
        for (int i = 0; i < list.size(); i++) {
        	for(int j=0;j<applicationNames.length;j++){
                if (list.get(i).getName().equals(applicationNames[j])) {
                	retChildList.add(list.get(i));
                	if(list.get(i).getVersion() != versions[j]){
                		versionChange = true;
                	}
                }
        	}
        }

        if(!versionChange){//如果版本没变化，返回null
        	return null;
        }

        ret.setName("virtualRoot");
        ret.setChildList(retChildList);

        return ret;
    }

    public static void main(String[] args) {
        PropertiesServiceImpl impl = new PropertiesServiceImpl();
        Node node = impl.getTree(new String[] {"uum", "card"}, new int[] {-1, -1});
        System.out.println(node);
    }
}
