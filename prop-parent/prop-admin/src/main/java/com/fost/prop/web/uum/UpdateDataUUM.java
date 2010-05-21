package com.fost.prop.web.uum;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fost.prop.api.model.Node;
import com.fost.prop.service.AdminService;
import com.fost.prop.web.uum.UpdateDataUUM;
import com.fost.uum.model.IDynaRightNode;
import com.fost.uum.model.IPermission;
import com.fost.uum.model.dto.PermissionDTO;

@Service("updateDataUUM")
public class UpdateDataUUM implements IDynaRightNode{
	private final static Logger LOGGER = LoggerFactory.getLogger(UpdateDataUUM.class);
	@Autowired
	@Qualifier("adminService")
	private AdminService adminService;

	@Override
	public List<IPermission> list() {
		LOGGER.info("uum enter this methed..............");
		List<Node> rootList = adminService.findNodesByParentId(-1);
		List<Node> nodeList = null;
		if (rootList != null) {
			nodeList = adminService.findNodesByParentId(rootList.get(0).getId());
		}

		LOGGER.info("list.size is :" + nodeList.size());

		List<IPermission> result = null;
		if (nodeList != null && nodeList.size() > 0) {
			result = new ArrayList<IPermission>();
			for (Node node : nodeList) {
				PermissionDTO p = new PermissionDTO();
				p.setName(String.valueOf(node.getId()));
				p.setShowName(node.getName());

				result.add(p);
			}
		}

		LOGGER.info("uum leave this methed..............");
		return result;
	}

}
