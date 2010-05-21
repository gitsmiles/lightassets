package com.fost.prop.web.controller;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fost.prop.api.model.Node;
import com.fost.prop.service.AdminService;
import com.fost.prop.web.wapper.AdminServiceWapper;

@Results(
        @Result(name = "success", type = "dispatcher", location = "data.ftl" )
)
public class NodeController  extends BaseController{
	@Autowired
	private AdminServiceWapper adminServiceWapper;
    @Autowired
    @Qualifier("adminService")
    private AdminService adminService;

	private int parentId;
	private int id;
	private String name;
	private String message;
	private Node node;
	private int data;
	private String sdata;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}



	public String getSdata() {
		return sdata;
	}

	public void setSdata(String sdata) {
		this.sdata = sdata;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public String create() {
		if (name == null || name.equals("")) {
			this.message = "the node's name cannot be null!";
			return "paramnull";
		}

		node = new Node();
		node.setParentId(parentId);
		node.setName(name);

		this.id = adminServiceWapper.addNode(node,opuser).getId();

		node.setId(id);
		return "addNode";
	}

    public String destroy() {
        data = adminServiceWapper.deleteNodeById(id,opuser);
        return "success";
    
    }

    public String edit() {
        Node node = adminService.findNodeById(id);
        if (node == null)
            sdata = "{result:0}";
        else
            sdata = "{result:1,target:{id:"+id+",name:\""+node.getName()+"\"}}";
        return "data";
    }
    
    public String update() {
        if(name == null || name.equals("")){
            message = "the node's name cannot be null!";
            return "paramnull";
        }

        Node node = adminService.findNodeById(id);
        node.setName(name);
        int rev = adminService.updateNode(node,opuser);
        if (rev == 0) {
            sdata = "0";
        } else {
            sdata =  "<a href=\"#\" target=\"mainFrame\"> " + name + "</a></span>";
        }
        return "data";
    }
}
