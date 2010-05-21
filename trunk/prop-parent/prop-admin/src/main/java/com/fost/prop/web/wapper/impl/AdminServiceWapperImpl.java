package com.fost.prop.web.wapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fost.prop.api.model.Node;
import com.fost.prop.model.Operator;
import com.fost.prop.service.AdminService;
import com.fost.prop.web.wapper.AdminServiceWapper;
import com.fost.uum.client.service.NotificationPermission;

@Service("adminServiceWapper")
public class AdminServiceWapperImpl implements AdminServiceWapper{
	@Autowired
	@Qualifier("adminService")
	private AdminService adminService;

	public Node addNode(Node node,Operator opuser) {
		Node result = adminService.addNode(node,opuser);

		NotificationPermission.notification();

		return result;

	}

	public int deleteNodeById(int nodeId,Operator opuser) {
		int result = adminService.deleteNodeById(nodeId,opuser);

		NotificationPermission.notification();

		return result;
	}

}
